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
  OPERATION1;
  OPERATION2;
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

package org.eclipse.tracecompass.tmf.ui.view.filter.parser;

import org.eclipse.tracecompass.tmf.ui.view.filter.parser.error.IErrorListener;
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

package org.eclipse.tracecompass.tmf.ui.view.filter.parser;

import org.eclipse.tracecompass.tmf.ui.view.filter.parser.error.IErrorListener;
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
           | TEXT OP_PRESENT -> ^(OPERATION1 TEXT OP_PRESENT)
           | (OP_NEGATE)(TEXT) -> ^(OPERATION2 OP_NEGATE TEXT)
           | TEXT  -> ^(CONSTANT TEXT)
           | '(' expr ')' -> ^(SIMPLE_EXP expr);

OP_PRESENT : 'PRESENT';
OP_NEGATE  : '!';
OP         : '==' | '!=' | 'CONTAINS' | 'MATCHES' | '>' | '<';
TEXT   : (('a'..'z')|('A'..'Z')|('0'..'9')|'-'|'_'|'['|']')+;

WS         : (' '|'\t'|'\r'|'\n')+ { skip(); } ;