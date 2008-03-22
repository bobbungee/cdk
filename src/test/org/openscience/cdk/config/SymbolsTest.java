/* $Revision: 7691 $ $Author: egonw $ $Date: 2007-01-11 12:47:48 +0100 (Thu, 11 Jan 2007) $
 * 
 * Copyright (C) 2007  Egon Willighagen <egonw@users.sf.net>
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
 */
package org.openscience.cdk.config;

import org.junit.Assert;
import org.junit.Test;

import org.openscience.cdk.NewCDKTestCase;

/**
 * @cdk.module test-standard
 */
public class SymbolsTest extends NewCDKTestCase {
    
	@Test public void testCarbon() {
		Assert.assertEquals("C", Symbols.byAtomicNumber[6]);
	}

	@Test public void testDummy() {
		Assert.assertEquals("Xx", Symbols.byAtomicNumber[0]);
	}
	
	@Test public void testLastEntry() {
		Assert.assertEquals("Uuh", Symbols.byAtomicNumber[116]);
	}

}

