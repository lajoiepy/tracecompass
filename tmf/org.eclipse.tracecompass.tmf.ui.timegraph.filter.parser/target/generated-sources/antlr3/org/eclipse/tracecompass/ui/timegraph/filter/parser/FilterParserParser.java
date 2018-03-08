// $ANTLR 3.5.2 org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g 2018-03-15 14:49:27

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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings("all")
public class FilterParserParser extends Parser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "CONSTANT", "EXP_NODE", "OP", 
		"OPERATION", "ROOT", "SEPARATOR", "SIMPLE_EXP", "TEXT", "WS", "'('", "')'"
	};
	public static final int EOF=-1;
	public static final int T__13=13;
	public static final int T__14=14;
	public static final int CONSTANT=4;
	public static final int EXP_NODE=5;
	public static final int OP=6;
	public static final int OPERATION=7;
	public static final int ROOT=8;
	public static final int SEPARATOR=9;
	public static final int SIMPLE_EXP=10;
	public static final int TEXT=11;
	public static final int WS=12;

	// delegates
	public Parser[] getDelegates() {
		return new Parser[] {};
	}

	// delegators


	public FilterParserParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public FilterParserParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	protected TreeAdaptor adaptor = new CommonTreeAdaptor();

	public void setTreeAdaptor(TreeAdaptor adaptor) {
		this.adaptor = adaptor;
	}
	public TreeAdaptor getTreeAdaptor() {
		return adaptor;
	}
	@Override public String[] getTokenNames() { return FilterParserParser.tokenNames; }
	@Override public String getGrammarFileName() { return "org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g"; }


	private IErrorListener errListener;

	public void setErrorListener(IErrorListener listener) {
	    errListener = listener;
	}

	@Override
	public void reportError(RecognitionException e) {
	    super.reportError(e);
	    errListener.error(e);
	}


	public static class parse_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "parse"
	// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:80:1: parse : ( expression )+ -> ^( ROOT ( expression )+ ) ;
	public final FilterParserParser.parse_return parse() throws RecognitionException {
		FilterParserParser.parse_return retval = new FilterParserParser.parse_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		ParserRuleReturnScope expression1 =null;

		RewriteRuleSubtreeStream stream_expression=new RewriteRuleSubtreeStream(adaptor,"rule expression");

		try {
			// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:80:7: ( ( expression )+ -> ^( ROOT ( expression )+ ) )
			// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:80:9: ( expression )+
			{
			// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:80:9: ( expression )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==TEXT||LA1_0==13) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:80:10: expression
					{
					pushFollow(FOLLOW_expression_in_parse114);
					expression1=expression();
					state._fsp--;

					stream_expression.add(expression1.getTree());
					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			// AST REWRITE
			// elements: expression
			// token labels: 
			// rule labels: retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (CommonTree)adaptor.nil();
			// 80:23: -> ^( ROOT ( expression )+ )
			{
				// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:80:26: ^( ROOT ( expression )+ )
				{
				CommonTree root_1 = (CommonTree)adaptor.nil();
				root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(ROOT, "ROOT"), root_1);
				if ( !(stream_expression.hasNext()) ) {
					throw new RewriteEarlyExitException();
				}
				while ( stream_expression.hasNext() ) {
					adaptor.addChild(root_1, stream_expression.nextTree());
				}
				stream_expression.reset();

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "parse"


	public static class expression_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "expression"
	// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:81:1: expression : left= expr (sep= SEPARATOR right= expr )? -> ^( EXP_NODE $left ( $sep $right)? ) ;
	public final FilterParserParser.expression_return expression() throws RecognitionException {
		FilterParserParser.expression_return retval = new FilterParserParser.expression_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token sep=null;
		ParserRuleReturnScope left =null;
		ParserRuleReturnScope right =null;

		CommonTree sep_tree=null;
		RewriteRuleTokenStream stream_SEPARATOR=new RewriteRuleTokenStream(adaptor,"token SEPARATOR");
		RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");

		try {
			// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:81:12: (left= expr (sep= SEPARATOR right= expr )? -> ^( EXP_NODE $left ( $sep $right)? ) )
			// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:81:14: left= expr (sep= SEPARATOR right= expr )?
			{
			pushFollow(FOLLOW_expr_in_expression138);
			left=expr();
			state._fsp--;

			stream_expr.add(left.getTree());
			// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:81:26: (sep= SEPARATOR right= expr )?
			int alt2=2;
			int LA2_0 = input.LA(1);
			if ( (LA2_0==SEPARATOR) ) {
				alt2=1;
			}
			switch (alt2) {
				case 1 :
					// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:81:27: sep= SEPARATOR right= expr
					{
					sep=(Token)match(input,SEPARATOR,FOLLOW_SEPARATOR_in_expression145);  
					stream_SEPARATOR.add(sep);

					pushFollow(FOLLOW_expr_in_expression151);
					right=expr();
					state._fsp--;

					stream_expr.add(right.getTree());
					}
					break;

			}

			// AST REWRITE
			// elements: left, sep, right
			// token labels: sep
			// rule labels: left, right, retval
			// token list labels: 
			// rule list labels: 
			// wildcard labels: 
			retval.tree = root_0;
			RewriteRuleTokenStream stream_sep=new RewriteRuleTokenStream(adaptor,"token sep",sep);
			RewriteRuleSubtreeStream stream_left=new RewriteRuleSubtreeStream(adaptor,"rule left",left!=null?left.getTree():null);
			RewriteRuleSubtreeStream stream_right=new RewriteRuleSubtreeStream(adaptor,"rule right",right!=null?right.getTree():null);
			RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

			root_0 = (CommonTree)adaptor.nil();
			// 81:58: -> ^( EXP_NODE $left ( $sep $right)? )
			{
				// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:81:61: ^( EXP_NODE $left ( $sep $right)? )
				{
				CommonTree root_1 = (CommonTree)adaptor.nil();
				root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(EXP_NODE, "EXP_NODE"), root_1);
				adaptor.addChild(root_1, stream_left.nextTree());
				// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:81:78: ( $sep $right)?
				if ( stream_sep.hasNext()||stream_right.hasNext() ) {
					adaptor.addChild(root_1, stream_sep.nextNode());
					adaptor.addChild(root_1, stream_right.nextTree());
				}
				stream_sep.reset();
				stream_right.reset();

				adaptor.addChild(root_0, root_1);
				}

			}


			retval.tree = root_0;

			}

			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "expression"


	public static class expr_return extends ParserRuleReturnScope {
		CommonTree tree;
		@Override
		public CommonTree getTree() { return tree; }
	};


	// $ANTLR start "expr"
	// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:85:1: expr : ( TEXT OP TEXT -> ^( OPERATION TEXT OP TEXT ) | TEXT -> ^( CONSTANT TEXT ) | '(' expr ')' -> ^( SIMPLE_EXP expr ) );
	public final FilterParserParser.expr_return expr() throws RecognitionException {
		FilterParserParser.expr_return retval = new FilterParserParser.expr_return();
		retval.start = input.LT(1);

		CommonTree root_0 = null;

		Token TEXT2=null;
		Token OP3=null;
		Token TEXT4=null;
		Token TEXT5=null;
		Token char_literal6=null;
		Token char_literal8=null;
		ParserRuleReturnScope expr7 =null;

		CommonTree TEXT2_tree=null;
		CommonTree OP3_tree=null;
		CommonTree TEXT4_tree=null;
		CommonTree TEXT5_tree=null;
		CommonTree char_literal6_tree=null;
		CommonTree char_literal8_tree=null;
		RewriteRuleTokenStream stream_OP=new RewriteRuleTokenStream(adaptor,"token OP");
		RewriteRuleTokenStream stream_13=new RewriteRuleTokenStream(adaptor,"token 13");
		RewriteRuleTokenStream stream_14=new RewriteRuleTokenStream(adaptor,"token 14");
		RewriteRuleTokenStream stream_TEXT=new RewriteRuleTokenStream(adaptor,"token TEXT");
		RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");

		try {
			// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:85:12: ( TEXT OP TEXT -> ^( OPERATION TEXT OP TEXT ) | TEXT -> ^( CONSTANT TEXT ) | '(' expr ')' -> ^( SIMPLE_EXP expr ) )
			int alt3=3;
			int LA3_0 = input.LA(1);
			if ( (LA3_0==TEXT) ) {
				int LA3_1 = input.LA(2);
				if ( (LA3_1==OP) ) {
					alt3=1;
				}
				else if ( (LA3_1==EOF||LA3_1==SEPARATOR||LA3_1==TEXT||(LA3_1 >= 13 && LA3_1 <= 14)) ) {
					alt3=2;
				}

				else {
					int nvaeMark = input.mark();
					try {
						input.consume();
						NoViableAltException nvae =
							new NoViableAltException("", 3, 1, input);
						throw nvae;
					} finally {
						input.rewind(nvaeMark);
					}
				}

			}
			else if ( (LA3_0==13) ) {
				alt3=3;
			}

			else {
				NoViableAltException nvae =
					new NoViableAltException("", 3, 0, input);
				throw nvae;
			}

			switch (alt3) {
				case 1 :
					// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:85:14: TEXT OP TEXT
					{
					TEXT2=(Token)match(input,TEXT,FOLLOW_TEXT_in_expr197);  
					stream_TEXT.add(TEXT2);

					OP3=(Token)match(input,OP,FOLLOW_OP_in_expr199);  
					stream_OP.add(OP3);

					TEXT4=(Token)match(input,TEXT,FOLLOW_TEXT_in_expr201);  
					stream_TEXT.add(TEXT4);

					// AST REWRITE
					// elements: TEXT, TEXT, OP
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (CommonTree)adaptor.nil();
					// 85:27: -> ^( OPERATION TEXT OP TEXT )
					{
						// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:85:30: ^( OPERATION TEXT OP TEXT )
						{
						CommonTree root_1 = (CommonTree)adaptor.nil();
						root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(OPERATION, "OPERATION"), root_1);
						adaptor.addChild(root_1, stream_TEXT.nextNode());
						adaptor.addChild(root_1, stream_OP.nextNode());
						adaptor.addChild(root_1, stream_TEXT.nextNode());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;

					}
					break;
				case 2 :
					// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:86:14: TEXT
					{
					TEXT5=(Token)match(input,TEXT,FOLLOW_TEXT_in_expr228);  
					stream_TEXT.add(TEXT5);

					// AST REWRITE
					// elements: TEXT
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (CommonTree)adaptor.nil();
					// 86:20: -> ^( CONSTANT TEXT )
					{
						// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:86:23: ^( CONSTANT TEXT )
						{
						CommonTree root_1 = (CommonTree)adaptor.nil();
						root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(CONSTANT, "CONSTANT"), root_1);
						adaptor.addChild(root_1, stream_TEXT.nextNode());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;

					}
					break;
				case 3 :
					// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:87:14: '(' expr ')'
					{
					char_literal6=(Token)match(input,13,FOLLOW_13_in_expr252);  
					stream_13.add(char_literal6);

					pushFollow(FOLLOW_expr_in_expr254);
					expr7=expr();
					state._fsp--;

					stream_expr.add(expr7.getTree());
					char_literal8=(Token)match(input,14,FOLLOW_14_in_expr256);  
					stream_14.add(char_literal8);

					// AST REWRITE
					// elements: expr
					// token labels: 
					// rule labels: retval
					// token list labels: 
					// rule list labels: 
					// wildcard labels: 
					retval.tree = root_0;
					RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.getTree():null);

					root_0 = (CommonTree)adaptor.nil();
					// 87:27: -> ^( SIMPLE_EXP expr )
					{
						// org/eclipse/tracecompass/ui/timegraph/filter/parser/FilterParser.g:87:30: ^( SIMPLE_EXP expr )
						{
						CommonTree root_1 = (CommonTree)adaptor.nil();
						root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(SIMPLE_EXP, "SIMPLE_EXP"), root_1);
						adaptor.addChild(root_1, stream_expr.nextTree());
						adaptor.addChild(root_0, root_1);
						}

					}


					retval.tree = root_0;

					}
					break;

			}
			retval.stop = input.LT(-1);

			retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
			adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
			retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);
		}
		finally {
			// do for sure before leaving
		}
		return retval;
	}
	// $ANTLR end "expr"

	// Delegated rules



	public static final BitSet FOLLOW_expression_in_parse114 = new BitSet(new long[]{0x0000000000002802L});
	public static final BitSet FOLLOW_expr_in_expression138 = new BitSet(new long[]{0x0000000000000202L});
	public static final BitSet FOLLOW_SEPARATOR_in_expression145 = new BitSet(new long[]{0x0000000000002800L});
	public static final BitSet FOLLOW_expr_in_expression151 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_TEXT_in_expr197 = new BitSet(new long[]{0x0000000000000040L});
	public static final BitSet FOLLOW_OP_in_expr199 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_TEXT_in_expr201 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_TEXT_in_expr228 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_13_in_expr252 = new BitSet(new long[]{0x0000000000002800L});
	public static final BitSet FOLLOW_expr_in_expr254 = new BitSet(new long[]{0x0000000000004000L});
	public static final BitSet FOLLOW_14_in_expr256 = new BitSet(new long[]{0x0000000000000002L});
}
