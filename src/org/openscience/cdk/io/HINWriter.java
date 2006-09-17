/* $RCSfile$
 * $Author$
 * $Date$
 * $Revision$
 *
 * Copyright (C) 2004-2006  The Chemistry Development Kit (CDK) project
 * 
 * Contact: cdk-devel@lists.sourceforge.net
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 */
package org.openscience.cdk.io;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.*;
import org.openscience.cdk.io.formats.HINFormat;
import org.openscience.cdk.io.formats.IResourceFormat;
import org.openscience.cdk.tools.LoggingTool;

import javax.vecmath.Point3d;
import java.io.*;
//import org.openscience.cdk.tools.LoggingTool;

/**
 * Writer that outputs in the HIN format.
 *
 * @author Rajarshi Guha <rajarshi@presidency.com>
 * @cdk.module io
 * @cdk.created 2004-01-27
 */
public class HINWriter extends DefaultChemObjectWriter {

    static BufferedWriter writer;

    /**
     * Constructor.
     *
     * @param out the stream to write the HIN file to.
     */
    public HINWriter(Writer out) {
        LoggingTool logger = new LoggingTool(this);
        try {
            if (out instanceof BufferedWriter) {
                writer = (BufferedWriter) out;
            } else {
                writer = new BufferedWriter(out);
            }
        } catch (Exception exc) {
            logger.debug(exc.toString());
        }
    }

    public HINWriter(OutputStream output) {
        this(new OutputStreamWriter(output));
    }

    public HINWriter() {
        this(new StringWriter());
    }

    public IResourceFormat getFormat() {
        return HINFormat.getInstance();
    }

    public void setWriter(Writer out) throws CDKException {
        if (out instanceof BufferedWriter) {
            writer = (BufferedWriter) out;
        } else {
            writer = new BufferedWriter(out);
        }
    }

    public void setWriter(OutputStream output) throws CDKException {
        setWriter(new OutputStreamWriter(output));
    }

    /**
     * Flushes the output and closes this object.
     */
    public void close() throws IOException {
        writer.close();
    }

    public boolean accepts(Class classObject) {
        Class[] interfaces = classObject.getInterfaces();
        for (int i = 0; i < interfaces.length; i++) {
            if (IMolecule.class.equals(interfaces[i])) return true;
            if (IMoleculeSet.class.equals(interfaces[i])) return true;
        }
        return false;
    }

    public void write(IChemObject object) throws CDKException {
        if (object instanceof IMolecule) {
            try {
                IMoleculeSet som = object.getBuilder().newMoleculeSet();
                som.addMolecule((IMolecule) object);
                writeMolecule(som);
            } catch (Exception ex) {
                throw new CDKException("Error while writing HIN file: " + ex.getMessage(), ex);
            }
        } else if (object instanceof IMoleculeSet) {
            try {
                writeMolecule((IMoleculeSet) object);
            } catch (IOException ex) {
                //
            }
        } else {
            throw new CDKException("HINWriter only supports output of Molecule or SetOfMolecule classes.");
        }
    }

    /**
     * writes all the molecules supplied in a SetOfMolecules class to
     * a single HIN file. You can also supply a single Molecule object
     * as well
     *
     * @param som the set of molecules to write
     */
    private void writeMolecule(IMoleculeSet som) throws IOException {

        //int na = 0;
        //String info = "";
        String sym;
        double chrg;
        //boolean writecharge = true;

        for (int molnum = 0; molnum < som.getMoleculeCount(); molnum++) {

            IMolecule mol = som.getMolecule(molnum);

            try {

                int natom = mol.getAtomCount();
                int nbond = mol.getBondCount();

                String molname = "mol " + (molnum + 1) + " " + mol.getProperty(CDKConstants.TITLE);

                writer.write(molname, 0, molname.length());
                writer.newLine();

                // Loop through the atoms and write them out:
                IAtom[] atoms = mol.getAtoms();
                IBond[] bonds = mol.getBonds();

                for (int i = 0; i < natom; i++) {

                    String line = "atom ";
                    IAtom a = atoms[i];

                    sym = a.getSymbol();
                    chrg = a.getCharge();
                    Point3d p3 = a.getPoint3d();

                    line = line + Integer.toString(i + 1) + " - " + sym + " ** - " +
                            Double.toString(chrg) + " " +
                            Double.toString(p3.x) + " " +
                            Double.toString(p3.y) + " " +
                            Double.toString(p3.z) + " ";

                    String buf = "";
                    int ncon = 0;
                    for (int j = 0; j < nbond; j++) {
                        IBond b = bonds[j];
                        if (b.contains(a)) {
                            // current atom is in the bond so lets get the connected atom
                            IAtom ca = b.getConnectedAtom(a);
                            double bo = b.getOrder();
                            int serial;
                            String bt = "";

                            // get the serial no for this atom
                            serial = mol.getAtomNumber(ca);

                            if (bo == 1) bt = "s";
                            else if (bo == 2) bt = "d";
                            else if (bo == 3) bt = "t";
                            else if (bo == 1.5) bt = "a";
                            buf = buf + Integer.toString(serial + 1) + " " + bt + " ";
                            ncon++;
                        }
                    }
                    line = line + " " + Integer.toString(ncon) + " " + buf;
                    writer.write(line, 0, line.length());
                    writer.newLine();
                }
                String buf = "endmol " + (molnum + 1);
                writer.write(buf, 0, buf.length());
                writer.newLine();
            } catch (IOException e) {
                throw e;
            }
        }
    }
}


