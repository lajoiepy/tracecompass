grammar FilterParser;

options {
    language   = Java;
    output     = AST;
    ASTLabelType = CommonTree;
}

tokens {
  ROOT;
  EXP_NODE;
  OPERATION;
  CONSTANT;
  SIMPLE_EXP;
}
@header {
/*******************************************************************************
 * Copyright (c) 2018 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jean-Christian Kouame - Initial API and implementation
 *******************************************************************************/

package org.eclipse.tracecompass.ui.timegraph.filter.parser;

import org.eclipse.tracecompass.ui.timegraph.filter.parser.error.IErrorListener;
}

@members {
private IErrorListener errListener;

public void setErrorListener(IErrorListener listener) {
    errListener = listener;
}

@Override
public void reportError(RecognitionException e) {
    super.reportError(e);
    errListener.error(e);
}
}

@lexer::header {
/*******************************************************************************
 * Copyright (c) 2018 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Jean-Christian Kouame - Initial API and implementation
 *******************************************************************************/

package org.eclipse.tracecompass.ui.timegraph.filter.parser;

import org.eclipse.tracecompass.ui.timegraph.filter.parser.error.IErrorListener;
}

@lexer::members {
private IErrorListener errListener;

public void setErrorListener(IErrorListener listener) {
    errListener = listener;
}

@Override
public void reportError(RecognitionException e) {
    super.reportError(e);
    errListener.error(e);
}
}

parse : (expression)+ -> ^(ROOT (expression)+);
expression : left = expr (sep = SEPARATOR right = expr)? -> ^(EXP_NODE $left ($sep $right)?);

SEPARATOR  :'||' | '&&';

expr       : TEXT OP TEXT -> ^(OPERATION TEXT OP TEXT)
           | TEXT  -> ^(CONSTANT TEXT)
           | '(' expr ')' -> ^(SIMPLE_EXP expr);

OP         : '==' | '!=' | 'CONTAINS' | 'MATCHES' | '>' | '<';
TEXT   : (('a'..'z')|('A'..'Z')|('0'..'9')|'-'|'_')+;

WS         : (' '|'\t'|'\r'|'\n')+ { skip(); } ;