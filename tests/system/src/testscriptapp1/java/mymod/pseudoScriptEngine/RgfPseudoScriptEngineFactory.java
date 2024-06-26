/*
 * Copyright (c) 2020, 2024, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package pseudoScriptEngine;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;

import java.lang.StringBuilder;
import java.util.Arrays;
import java.util.List;


public class RgfPseudoScriptEngineFactory implements ScriptEngineFactory {
    static final String       ENGINE_NAME       = "RgfPseudoScriptLanguage (RPSL) 1.0.0";
    static final String       SHORT_ENGINE_NAME = "rpsl";
    static final String       ENGINE_VERSION    = "100.20200228";
    static final List<String> EXTENSIONS        = Arrays.asList("rpsl", "RPSL");
    static final String       LANGUAGE_NAME     = "RgfPseudoScriptLanguage";
    static final String       LANGUAGE_VERSION  = "1.0.0.100.20200228";
    static final List<String> MIME_TYPES        = Arrays.asList("text/rpsl", "application/x-rpsl");
    static final String       THREADING         = "MULTITHREADED";
    static final List<String> ENGINE_NAMES      = Arrays.asList(SHORT_ENGINE_NAME, "RgfPseudoSL");

    @Override
    public String getEngineName() {
        return ENGINE_NAME;
    }

    @Override
    public String getEngineVersion() {
        return ENGINE_VERSION;
    }

    @Override
    public List<String> getExtensions() {
        return EXTENSIONS;
    }

    @Override
    public String getLanguageName() {
        return LANGUAGE_NAME;
    }

    @Override
    public String getLanguageVersion() {
        return LANGUAGE_VERSION;
    }

    public String getName() {
        return ENGINE_NAME;
    }

    @Override
    public String getMethodCallSyntax(String obj, String m, String... args) {
        return "obj~(m, ...) /* ooRexx style */ ";
    }

    @Override
    public List<String> getMimeTypes() {
        return MIME_TYPES;
    }

    @Override
    public List<String> getNames() {
        return ENGINE_NAMES;
    }

    @Override
    public String getOutputStatement(String toDisplay) {
        String tmpDisplay = toStringLiteral(toDisplay);
        return "say " + tmpDisplay + " /* Rexx style (duplicate quotes within string) */ ";
    }

    String toStringLiteral(String toDisplay) {
        if (toDisplay == null) {
            return "\"\"";
        }
        return '"' + toDisplay.replace("\"","\"\"") + '"';
    }

    @Override
    public Object getParameter(final String key) {
        switch (key) {
            case "THREADING":
                return THREADING;

            case ScriptEngine.NAME:
                return SHORT_ENGINE_NAME;

            case ScriptEngine.ENGINE:
                return ENGINE_NAME;

            case ScriptEngine.ENGINE_VERSION:
                return ENGINE_VERSION;

            case ScriptEngine.LANGUAGE:
                return LANGUAGE_NAME;

            case ScriptEngine.LANGUAGE_VERSION:
                 return LANGUAGE_VERSION;

            default:
                return null;
        }
    }

    @Override
    public String getProgram(String... statements) {
        if (statements == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < statements.length; i++) {
            if (statements[i] == null) {
                sb.append("\tsay 'null'; /* Rexx style */ \n");
            }
            else {
                sb.append("\t" + statements[i] + ";\n");
            }
        }
        return sb.toString();
    }

    @Override
    public ScriptEngine getScriptEngine() {
        return new RgfPseudoScriptEngine();
    }
}
