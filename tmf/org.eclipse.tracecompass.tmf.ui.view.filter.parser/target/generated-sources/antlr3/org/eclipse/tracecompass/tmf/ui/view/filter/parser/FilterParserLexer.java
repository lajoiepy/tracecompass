// $ANTLR 3.5.2 org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g 2018-03-23 17:03:36

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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class FilterParserLexer extends Lexer {
	public static final int EOF=-1;
	public static final int T__17=17;
	public static final int T__18=18;
	public static final int CONSTANT=4;
	public static final int EXP_NODE=5;
	public static final int OP=6;
	public static final int OPERATION=7;
	public static final int OPERATION1=8;
	public static final int OPERATION2=9;
	public static final int OP_NEGATE=10;
	public static final int OP_PRESENT=11;
	public static final int ROOT=12;
	public static final int SEPARATOR=13;
	public static final int SIMPLE_EXP=14;
	public static final int TEXT=15;
	public static final int WS=16;

	private IErrorListener errListener;

	public void setErrorListener(IErrorListener listener) {
	    errListener = listener;
	}

	@Override
	public void reportError(RecognitionException e) {
	    super.reportError(e);
	    errListener.error(e);
	}


	// delegates
	// delegators
	public Lexer[] getDelegates() {
		return new Lexer[] {};
	}

	public FilterParserLexer() {} 
	public FilterParserLexer(CharStream input) {
		this(input, new RecognizerSharedState());
	}
	public FilterParserLexer(CharStream input, RecognizerSharedState state) {
		super(input,state);
	}
	@Override public String getGrammarFileName() { return "org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g"; }

	// $ANTLR start "T__17"
	public final void mT__17() throws RecognitionException {
		try {
			int _type = T__17;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:38:7: ( '(' )
			// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:38:9: '('
			{
			match('('); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__17"

	// $ANTLR start "T__18"
	public final void mT__18() throws RecognitionException {
		try {
			int _type = T__18;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:39:7: ( ')' )
			// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:39:9: ')'
			{
			match(')'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "T__18"

	// $ANTLR start "SEPARATOR"
	public final void mSEPARATOR() throws RecognitionException {
		try {
			int _type = SEPARATOR;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:85:12: ( '||' | '&&' )
			int alt1=2;
			int LA1_0 = input.LA(1);
			if ( (LA1_0=='|') ) {
				alt1=1;
			}
			else if ( (LA1_0=='&') ) {
				alt1=2;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 1, 0, input);
				throw nvae;
			}

			switch (alt1) {
				case 1 :
					// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:85:13: '||'
					{
					match("||"); 

					}
					break;
				case 2 :
					// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:85:20: '&&'
					{
					match("&&"); 

					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "SEPARATOR"

	// $ANTLR start "OP_PRESENT"
	public final void mOP_PRESENT() throws RecognitionException {
		try {
			int _type = OP_PRESENT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:93:12: ( 'PRESENT' )
			// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:93:14: 'PRESENT'
			{
			match("PRESENT"); 

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "OP_PRESENT"

	// $ANTLR start "OP_NEGATE"
	public final void mOP_NEGATE() throws RecognitionException {
		try {
			int _type = OP_NEGATE;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:94:12: ( '!' )
			// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:94:14: '!'
			{
			match('!'); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "OP_NEGATE"

	// $ANTLR start "OP"
	public final void mOP() throws RecognitionException {
		try {
			int _type = OP;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:95:12: ( '==' | '!=' | 'CONTAINS' | 'MATCHES' | '>' | '<' )
			int alt2=6;
			switch ( input.LA(1) ) {
			case '=':
				{
				alt2=1;
				}
				break;
			case '!':
				{
				alt2=2;
				}
				break;
			case 'C':
				{
				alt2=3;
				}
				break;
			case 'M':
				{
				alt2=4;
				}
				break;
			case '>':
				{
				alt2=5;
				}
				break;
			case '<':
				{
				alt2=6;
				}
				break;
			default:
				NoViableAltException nvae =
					new NoViableAltException("", 2, 0, input);
				throw nvae;
			}
			switch (alt2) {
				case 1 :
					// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:95:14: '=='
					{
					match("=="); 

					}
					break;
				case 2 :
					// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:95:21: '!='
					{
					match("!="); 

					}
					break;
				case 3 :
					// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:95:28: 'CONTAINS'
					{
					match("CONTAINS"); 

					}
					break;
				case 4 :
					// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:95:41: 'MATCHES'
					{
					match("MATCHES"); 

					}
					break;
				case 5 :
					// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:95:53: '>'
					{
					match('>'); 
					}
					break;
				case 6 :
					// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:95:59: '<'
					{
					match('<'); 
					}
					break;

			}
			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "OP"

	// $ANTLR start "TEXT"
	public final void mTEXT() throws RecognitionException {
		try {
			int _type = TEXT;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:96:8: ( ( ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) | ( '0' .. '9' ) | '-' | '_' | '[' | ']' )+ )
			// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:96:10: ( ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) | ( '0' .. '9' ) | '-' | '_' | '[' | ']' )+
			{
			// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:96:10: ( ( 'a' .. 'z' ) | ( 'A' .. 'Z' ) | ( '0' .. '9' ) | '-' | '_' | '[' | ']' )+
			int cnt3=0;
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( (LA3_0=='-'||(LA3_0 >= '0' && LA3_0 <= '9')||(LA3_0 >= 'A' && LA3_0 <= '[')||LA3_0==']'||LA3_0=='_'||(LA3_0 >= 'a' && LA3_0 <= 'z')) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:
					{
					if ( input.LA(1)=='-'||(input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= '[')||input.LA(1)==']'||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z') ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt3 >= 1 ) break loop3;
					EarlyExitException eee = new EarlyExitException(3, input);
					throw eee;
				}
				cnt3++;
			}

			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "TEXT"

	// $ANTLR start "WS"
	public final void mWS() throws RecognitionException {
		try {
			int _type = WS;
			int _channel = DEFAULT_TOKEN_CHANNEL;
			// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:98:12: ( ( ' ' | '\\t' | '\\r' | '\\n' )+ )
			// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:98:14: ( ' ' | '\\t' | '\\r' | '\\n' )+
			{
			// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:98:14: ( ' ' | '\\t' | '\\r' | '\\n' )+
			int cnt4=0;
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( ((LA4_0 >= '\t' && LA4_0 <= '\n')||LA4_0=='\r'||LA4_0==' ') ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:
					{
					if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
						input.consume();
					}
					else {
						MismatchedSetException mse = new MismatchedSetException(null,input);
						recover(mse);
						throw mse;
					}
					}
					break;

				default :
					if ( cnt4 >= 1 ) break loop4;
					EarlyExitException eee = new EarlyExitException(4, input);
					throw eee;
				}
				cnt4++;
			}

			 skip(); 
			}

			state.type = _type;
			state.channel = _channel;
		}
		finally {
			// do for sure before leaving
		}
	}
	// $ANTLR end "WS"

	@Override
	public void mTokens() throws RecognitionException {
		// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:1:8: ( T__17 | T__18 | SEPARATOR | OP_PRESENT | OP_NEGATE | OP | TEXT | WS )
		int alt5=8;
		switch ( input.LA(1) ) {
		case '(':
			{
			alt5=1;
			}
			break;
		case ')':
			{
			alt5=2;
			}
			break;
		case '&':
		case '|':
			{
			alt5=3;
			}
			break;
		case 'P':
			{
			int LA5_4 = input.LA(2);
			if ( (LA5_4=='R') ) {
				int LA5_11 = input.LA(3);
				if ( (LA5_11=='E') ) {
					int LA5_15 = input.LA(4);
					if ( (LA5_15=='S') ) {
						int LA5_18 = input.LA(5);
						if ( (LA5_18=='E') ) {
							int LA5_21 = input.LA(6);
							if ( (LA5_21=='N') ) {
								int LA5_24 = input.LA(7);
								if ( (LA5_24=='T') ) {
									int LA5_27 = input.LA(8);
									if ( (LA5_27=='-'||(LA5_27 >= '0' && LA5_27 <= '9')||(LA5_27 >= 'A' && LA5_27 <= '[')||LA5_27==']'||LA5_27=='_'||(LA5_27 >= 'a' && LA5_27 <= 'z')) ) {
										alt5=7;
									}

									else {
										alt5=4;
									}

								}

								else {
									alt5=7;
								}

							}

							else {
								alt5=7;
							}

						}

						else {
							alt5=7;
						}

					}

					else {
						alt5=7;
					}

				}

				else {
					alt5=7;
				}

			}

			else {
				alt5=7;
			}

			}
			break;
		case '!':
			{
			int LA5_5 = input.LA(2);
			if ( (LA5_5=='=') ) {
				alt5=6;
			}

			else {
				alt5=5;
			}

			}
			break;
		case '<':
		case '=':
		case '>':
			{
			alt5=6;
			}
			break;
		case 'C':
			{
			int LA5_7 = input.LA(2);
			if ( (LA5_7=='O') ) {
				int LA5_13 = input.LA(3);
				if ( (LA5_13=='N') ) {
					int LA5_16 = input.LA(4);
					if ( (LA5_16=='T') ) {
						int LA5_19 = input.LA(5);
						if ( (LA5_19=='A') ) {
							int LA5_22 = input.LA(6);
							if ( (LA5_22=='I') ) {
								int LA5_25 = input.LA(7);
								if ( (LA5_25=='N') ) {
									int LA5_28 = input.LA(8);
									if ( (LA5_28=='S') ) {
										int LA5_31 = input.LA(9);
										if ( (LA5_31=='-'||(LA5_31 >= '0' && LA5_31 <= '9')||(LA5_31 >= 'A' && LA5_31 <= '[')||LA5_31==']'||LA5_31=='_'||(LA5_31 >= 'a' && LA5_31 <= 'z')) ) {
											alt5=7;
										}

										else {
											alt5=6;
										}

									}

									else {
										alt5=7;
									}

								}

								else {
									alt5=7;
								}

							}

							else {
								alt5=7;
							}

						}

						else {
							alt5=7;
						}

					}

					else {
						alt5=7;
					}

				}

				else {
					alt5=7;
				}

			}

			else {
				alt5=7;
			}

			}
			break;
		case 'M':
			{
			int LA5_8 = input.LA(2);
			if ( (LA5_8=='A') ) {
				int LA5_14 = input.LA(3);
				if ( (LA5_14=='T') ) {
					int LA5_17 = input.LA(4);
					if ( (LA5_17=='C') ) {
						int LA5_20 = input.LA(5);
						if ( (LA5_20=='H') ) {
							int LA5_23 = input.LA(6);
							if ( (LA5_23=='E') ) {
								int LA5_26 = input.LA(7);
								if ( (LA5_26=='S') ) {
									int LA5_29 = input.LA(8);
									if ( (LA5_29=='-'||(LA5_29 >= '0' && LA5_29 <= '9')||(LA5_29 >= 'A' && LA5_29 <= '[')||LA5_29==']'||LA5_29=='_'||(LA5_29 >= 'a' && LA5_29 <= 'z')) ) {
										alt5=7;
									}

									else {
										alt5=6;
									}

								}

								else {
									alt5=7;
								}

							}

							else {
								alt5=7;
							}

						}

						else {
							alt5=7;
						}

					}

					else {
						alt5=7;
					}

				}

				else {
					alt5=7;
				}

			}

			else {
				alt5=7;
			}

			}
			break;
		case '-':
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
		case 'A':
		case 'B':
		case 'D':
		case 'E':
		case 'F':
		case 'G':
		case 'H':
		case 'I':
		case 'J':
		case 'K':
		case 'L':
		case 'N':
		case 'O':
		case 'Q':
		case 'R':
		case 'S':
		case 'T':
		case 'U':
		case 'V':
		case 'W':
		case 'X':
		case 'Y':
		case 'Z':
		case '[':
		case ']':
		case '_':
		case 'a':
		case 'b':
		case 'c':
		case 'd':
		case 'e':
		case 'f':
		case 'g':
		case 'h':
		case 'i':
		case 'j':
		case 'k':
		case 'l':
		case 'm':
		case 'n':
		case 'o':
		case 'p':
		case 'q':
		case 'r':
		case 's':
		case 't':
		case 'u':
		case 'v':
		case 'w':
		case 'x':
		case 'y':
		case 'z':
			{
			alt5=7;
			}
			break;
		case '\t':
		case '\n':
		case '\r':
		case ' ':
			{
			alt5=8;
			}
			break;
		default:
			NoViableAltException nvae =
				new NoViableAltException("", 5, 0, input);
			throw nvae;
		}
		switch (alt5) {
			case 1 :
				// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:1:10: T__17
				{
				mT__17(); 

				}
				break;
			case 2 :
				// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:1:16: T__18
				{
				mT__18(); 

				}
				break;
			case 3 :
				// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:1:22: SEPARATOR
				{
				mSEPARATOR(); 

				}
				break;
			case 4 :
				// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:1:32: OP_PRESENT
				{
				mOP_PRESENT(); 

				}
				break;
			case 5 :
				// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:1:43: OP_NEGATE
				{
				mOP_NEGATE(); 

				}
				break;
			case 6 :
				// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:1:53: OP
				{
				mOP(); 

				}
				break;
			case 7 :
				// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:1:56: TEXT
				{
				mTEXT(); 

				}
				break;
			case 8 :
				// org/eclipse/tracecompass/tmf/ui/view/filter/parser/FilterParser.g:1:61: WS
				{
				mWS(); 

				}
				break;

		}
	}



}
