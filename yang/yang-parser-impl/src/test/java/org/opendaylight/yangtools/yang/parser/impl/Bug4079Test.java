/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.yangtools.yang.parser.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.junit.Test;
import org.opendaylight.yangtools.yang.model.api.Module;

public class Bug4079Test {

    @Test
    public void testModuleCompilation() throws URISyntaxException, IOException {
        Set<Module> modules = TestUtils.loadModules(getClass().getResource("/bugs/bug4079").toURI());
        assertNotNull(modules);
    }

    @Test
    public void testValidPatternFix() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = ParserListenerUtils.class.getDeclaredMethod("fixUnicodeScriptPattern", String.class);
        assertNotNull(method);
        assertEquals("fixUnicodeScriptPattern", method.getName());

        method.setAccessible(true);

        String fixedUnicodeScriptPattern = (String) method.invoke(null,"(\\p{IsArrows})*+");
        assertEquals("(\\p{InArrows})*+", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));

        fixedUnicodeScriptPattern = (String) method.invoke(null,"(\\p{IsDingbats})++");
        assertEquals("(\\p{InDingbats})++", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));

        fixedUnicodeScriptPattern = (String) method.invoke(null,"(\\p{IsSpecials})?+");
        assertEquals("(\\p{InSpecials})?+", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));

        fixedUnicodeScriptPattern = (String) method.invoke(null,"(\\p{IsBatak}){4}+");
        assertEquals("(\\p{IsBatak}){4}+", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));

        fixedUnicodeScriptPattern = (String) method.invoke(null,"(\\p{IsLatin}){4,6}+");
        assertEquals("(\\p{IsLatin}){4,6}+", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));

        fixedUnicodeScriptPattern = (String) method.invoke(null,"(\\p{IsTibetan}){4,}+");
        assertEquals("(\\p{IsTibetan}){4,}+", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));

        fixedUnicodeScriptPattern = (String) method.invoke(null, "(\\p{IsAlphabetic}){4}?");
        assertEquals("(\\p{IsAlphabetic}){4}?", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));

        fixedUnicodeScriptPattern = (String) method.invoke(null, "(\\p{IsLowercase}){4,6}?");
        assertEquals("(\\p{IsLowercase}){4,6}?", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));

        fixedUnicodeScriptPattern = (String) method.invoke(null, "(\\p{IsUppercase}){4,}?");
        assertEquals("(\\p{IsUppercase}){4,}?", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));

        fixedUnicodeScriptPattern = (String) method.invoke(null,"(\\p{IsBasicLatin}|\\p{IsLatin-1Supplement})*");
        assertEquals("(\\p{InBasicLatin}|\\p{InLatin-1Supplement})*", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));

        fixedUnicodeScriptPattern = (String) method.invoke(null, "(\\p{InBasicLatin}|\\p{InLatin-1Supplement})+");
        assertEquals("(\\p{InBasicLatin}|\\p{InLatin-1Supplement})+", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));

        fixedUnicodeScriptPattern = (String) method.invoke(null, "(\\p{IsBasicLatin}|\\p{InLatin-1Supplement})?");
        assertEquals("(\\p{InBasicLatin}|\\p{InLatin-1Supplement})?", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));

        fixedUnicodeScriptPattern = (String) method.invoke(null, "(\\p{InBasicLatin}|\\p{IsLatin-1Supplement}){4}");
        assertEquals("(\\p{InBasicLatin}|\\p{InLatin-1Supplement}){4}", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));

        fixedUnicodeScriptPattern = (String) method.invoke(null, "(\\p{IsLatin}|\\p{IsArmenian}){2,4}");
        assertEquals("(\\p{IsLatin}|\\p{IsArmenian}){2,4}", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));

        fixedUnicodeScriptPattern = (String) method.invoke(null, "(\\p{IsLatin}|\\p{IsBasicLatin}){2,}");
        assertEquals("(\\p{IsLatin}|\\p{InBasicLatin}){2,}", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));

        fixedUnicodeScriptPattern = (String) method.invoke(null, "(\\p{IsBasicLatin}|\\p{IsLatin})*?");
        assertEquals("(\\p{InBasicLatin}|\\p{IsLatin})*?", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));

        fixedUnicodeScriptPattern = (String) method.invoke(null, "(\\p{IsBasicLatin}|\\p{IsLatin-1Supplement}" +
                "|\\p{IsArrows})+?");
        assertEquals("(\\p{InBasicLatin}|\\p{InLatin-1Supplement}|\\p{InArrows})+?", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));

        fixedUnicodeScriptPattern = (String) method.invoke(null, "(\\p{InBasicLatin}|\\p{IsLatin-1Supplement}|" +
                "\\p{IsLatin})??");
        assertEquals("(\\p{InBasicLatin}|\\p{InLatin-1Supplement}|\\p{IsLatin})??", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));

        fixedUnicodeScriptPattern = (String) method.invoke(null,"(\\\\\\p{IsBasicLatin})*+");
        assertEquals("(\\\\\\p{InBasicLatin})*+", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));

        fixedUnicodeScriptPattern = (String) method.invoke(null,"(\\\\\\\\\\p{IsBasicLatin})*+");
        assertEquals("(\\\\\\\\\\p{InBasicLatin})*+", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));

        fixedUnicodeScriptPattern = (String) method.invoke(null,"(\\\\\\\\\\\\\\p{IsBasicLatin})*+");
        assertEquals("(\\\\\\\\\\\\\\p{InBasicLatin})*+", fixedUnicodeScriptPattern);
        assertNotNull(Pattern.compile(fixedUnicodeScriptPattern));
    }

    @Test(expected = PatternSyntaxException.class)
    public void testInvalidPattern() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = ParserListenerUtils.class.getDeclaredMethod("fixUnicodeScriptPattern", String.class);
        assertNotNull(method);
        assertEquals("fixUnicodeScriptPattern", method.getName());

        method.setAccessible(true);

        String fixedUnicodeScriptPattern = (String) method.invoke(null,"(\\\\p{IsBasicLatin})*+");
        assertEquals("(\\\\p{IsBasicLatin})*+", fixedUnicodeScriptPattern);
        // should throw exception
        Pattern.compile(fixedUnicodeScriptPattern);
    }

    @Test(expected = PatternSyntaxException.class)
    public void testInvalidPattern2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = ParserListenerUtils.class.getDeclaredMethod("fixUnicodeScriptPattern", String.class);
        assertNotNull(method);
        assertEquals("fixUnicodeScriptPattern", method.getName());

        method.setAccessible(true);

        String fixedUnicodeScriptPattern = (String) method.invoke(null,"(\\p{IsSpecials}|\\\\\\\\p{IsBasicLatin})*+");
        assertEquals("(\\p{InSpecials}|\\\\\\\\p{IsBasicLatin})*+", fixedUnicodeScriptPattern);
        // should throw exception
        Pattern.compile(fixedUnicodeScriptPattern);
    }

    @Test(expected = PatternSyntaxException.class)
    public void testInvalidPattern3() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = ParserListenerUtils.class.getDeclaredMethod("fixUnicodeScriptPattern", String.class);
        assertNotNull(method);
        assertEquals("fixUnicodeScriptPattern", method.getName());

        method.setAccessible(true);

        String fixedUnicodeScriptPattern = (String) method.invoke(null,"(\\\\\\\\\\\\p{IsBasicLatin}|\\p{IsTags})*+");
        assertEquals("(\\\\\\\\\\\\p{IsBasicLatin}|\\p{IsTags})*+", fixedUnicodeScriptPattern);
        // should throw exception
        Pattern.compile(fixedUnicodeScriptPattern);
    }
}
