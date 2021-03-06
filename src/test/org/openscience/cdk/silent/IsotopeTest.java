/* $RCSfile$
 * $Author$    
 * $Date$    
 * $Revision$
 * 
 * Copyright (C) 1997-2007  The Chemistry Development Kit (CDK) project
 * 
 * Contact: cdk-devel@lists.sourceforge.net
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA. 
 * 
 */
package org.openscience.cdk.silent;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.interfaces.IElement;
import org.openscience.cdk.interfaces.IIsotope;
import org.openscience.cdk.interfaces.AbstractIsotopeTest;
import org.openscience.cdk.interfaces.ITestObjectBuilder;

/**
 * Checks the functionality of the {@link Isotope}.
 *
 * @cdk.module test-silent
 */
public class IsotopeTest extends AbstractIsotopeTest {

    @BeforeClass public static void setUp() {
        setTestObjectBuilder(new ITestObjectBuilder() {
            public IChemObject newTestObject() {
                return new Isotope(new Element());
            }
        });
    }

    @Test public void testIsotope_String() {
        IIsotope i = new Isotope("C");
        Assert.assertEquals("C", i.getSymbol());
    }
    
    @Test public void testIsotope_IElement() {
    	IElement element = newChemObject().getBuilder().newInstance(IElement.class,"C");
        IIsotope i = new Isotope(element);
        Assert.assertEquals("C", i.getSymbol());
    }
    
    @Test public void testIsotope_int_String_int_double_double() {
        IIsotope i = new Isotope(6, "C", 12, 12.001, 80.0);
        Assert.assertEquals(12, i.getMassNumber().intValue());
        Assert.assertEquals("C", i.getSymbol());
        Assert.assertEquals(6, i.getAtomicNumber().intValue());
        Assert.assertEquals(12.001, i.getExactMass(), 0.001);
        Assert.assertEquals(80.0, i.getNaturalAbundance(), 0.001);
    }
    
    @Test public void testIsotope_String_int() {
        IIsotope i = new Isotope("C", 12);
        Assert.assertEquals(12, i.getMassNumber().intValue());
        Assert.assertEquals("C", i.getSymbol());
    }
    
    @Test public void testIsotope_int_String_double_double() {
        IIsotope i = new Isotope(6, "C", 12.001, 80.0);
        Assert.assertEquals("C", i.getSymbol());
        Assert.assertEquals(6, i.getAtomicNumber().intValue());
        Assert.assertEquals(12.001, i.getExactMass(), 0.001);
        Assert.assertEquals(80.0, i.getNaturalAbundance(), 0.001);
    }

    // Overwrite default methods: no notifications are expected!
    
    @Test public void testNotifyChanged() {
        ChemObjectTestHelper.testNotifyChanged(newChemObject());
    }
    @Test public void testNotifyChanged_SetFlag() {
        ChemObjectTestHelper.testNotifyChanged_SetFlag(newChemObject());
    }
    @Test public void testNotifyChanged_SetFlags() {
        ChemObjectTestHelper.testNotifyChanged_SetFlags(newChemObject());
    }
    @Test public void testNotifyChanged_IChemObjectChangeEvent() {
        ChemObjectTestHelper.testNotifyChanged_IChemObjectChangeEvent(newChemObject());
    }
    @Test public void testStateChanged_IChemObjectChangeEvent() {
        ChemObjectTestHelper.testStateChanged_IChemObjectChangeEvent(newChemObject());
    }
    @Test public void testClone_ChemObjectListeners() throws Exception {
        ChemObjectTestHelper.testClone_ChemObjectListeners(newChemObject());
    }
    @Test public void testAddListener_IChemObjectListener() {
        ChemObjectTestHelper.testAddListener_IChemObjectListener(newChemObject());
    }
    @Test public void testGetListenerCount() {
        ChemObjectTestHelper.testGetListenerCount(newChemObject());
    }
    @Test public void testRemoveListener_IChemObjectListener() {
        ChemObjectTestHelper.testRemoveListener_IChemObjectListener(newChemObject());
    }
    @Test public void testSetNotification_true() {
        ChemObjectTestHelper.testSetNotification_true(newChemObject());
    }
    @Test public void testNotifyChanged_SetProperty() {
        ChemObjectTestHelper.testNotifyChanged_SetProperty(newChemObject());
    }
    @Test public void testNotifyChanged_RemoveProperty() {
        ChemObjectTestHelper.testNotifyChanged_RemoveProperty(newChemObject());
    }
}
