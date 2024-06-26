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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngineFactory;
import javax.script.SimpleBindings;

public class RgfPseudoScriptEngine extends AbstractScriptEngine {
    static final boolean bDebug = false; // true;

    /** Allows to log and access the ScriptEngine instances with their evalDataList. */
    static final ArrayList<RgfPseudoScriptEngine> enginesUsed = new ArrayList();
    public static ArrayList<RgfPseudoScriptEngine> getEnginesUsed() {
        return enginesUsed;
    }

    public RgfPseudoScriptEngine() {
        enginesUsed.add(this);
    }

    @Override
    public ScriptEngineFactory getFactory() {
        return new RgfPseudoScriptEngineFactory();
    }

    /** ArrayList of eval() (invocation) information. */
    final ArrayList<InvocationInfos> invocationList = new ArrayList();

    /** Returns ArrayList of eval() (invocation) information.
     * @return invocationList
    */
    public ArrayList<InvocationInfos> getInvocationList() {
        return invocationList;
    }

    @Override
    public Bindings createBindings() {
        return new SimpleBindings();
    }

    @Override
    public Object eval(Reader reader, ScriptContext context) {
        if (bDebug) System.err.println("[debug: " + this + ".eval(Reader,ScriptContext), ScriptContext=" + context + "]");

        return eval(readReader(reader), context);
    }


    @Override
    public Object eval(String script, ScriptContext context) {
        if (bDebug) System.err.print("[debug: " + this + ".eval(String,ScriptContext), ScriptContext=" + context + "]");

        // create copies of the Bindings for later inspection as they may
        // get reused and changed on each eval() invocation
        TreeMap<Integer,TreeMap> bindings = new TreeMap();
        for (Integer scope : context.getScopes()) {
            Bindings binding = context.getBindings(scope);
            bindings.put(scope, binding == null ? new TreeMap<String,Object>() : new TreeMap<String,Object>(binding));
        }
        invocationList.add(new InvocationInfos(script,context));
        if (bDebug) System.err.println(" | invocationList.size()=" + invocationList.size());
        return invocationList;
    }


    String readReader(Reader reader) {
        if (reader == null) {
            return "";
        }

        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        // caters for possible IOException in read() and close()
        try {
            try {
                char[] charBuffer = new char[1024];
                int r = 0;

                while ((r = bufferedReader.read(charBuffer)) != -1) {
                    sb.append(charBuffer, 0, r);
                }
            } finally {
                bufferedReader.close();
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe.getMessage(), ioe);
        }
        return sb.toString();
    }
}
