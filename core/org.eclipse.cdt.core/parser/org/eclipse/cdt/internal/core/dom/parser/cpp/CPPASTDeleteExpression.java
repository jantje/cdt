/*******************************************************************************
 * Copyright (c) 2004, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * IBM - Initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.internal.core.dom.parser.cpp;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IType;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTDeleteExpression;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.cdt.internal.core.dom.parser.cpp.semantics.CPPSemantics;

/**
 * @author jcamelon
 */
public class CPPASTDeleteExpression extends ASTNode implements ICPPASTDeleteExpression {

    private IASTExpression operand;
    private boolean isGlobal;
    private boolean isVectored;

    
    public CPPASTDeleteExpression() {
	}

	public CPPASTDeleteExpression(IASTExpression operand) {
		setOperand(operand);
	}
	
	public CPPASTDeleteExpression(CPPASTDeleteExpression from) {
		setOperand(from.operand);
	}
	
	public CPPASTDeleteExpression copy() {
		CPPASTDeleteExpression copy = new CPPASTDeleteExpression(operand == null ? null : operand.copy());
		copy.isGlobal = isGlobal;
		copy.isVectored = isVectored;
		copy.setOffsetAndLength(this);
		return copy;
	}
	
	public IASTExpression getOperand() {
        return operand;
    }

    public void setOperand(IASTExpression expression) {
        assertNotFrozen();
        operand = expression;
        if (expression != null) {
			expression.setParent(this);
			expression.setPropertyInParent(OPERAND);
		}
    }

    public void setIsGlobal(boolean global) {
        assertNotFrozen();
        isGlobal = global;
    }

    public boolean isGlobal() {
        return isGlobal;
    }

    public void setIsVectored(boolean vectored) {
        assertNotFrozen();
        isVectored = vectored;
    }

    public boolean isVectored() {
        return isVectored;
    }

    @Override
	public boolean accept( ASTVisitor action ){
        if( action.shouldVisitExpressions ){
		    switch( action.visit( this ) ){
	            case ASTVisitor.PROCESS_ABORT : return false;
	            case ASTVisitor.PROCESS_SKIP  : return true;
	            default : break;
	        }
		}
        
        if( operand != null ) if( !operand.accept( action ) ) return false;
        
        if( action.shouldVisitExpressions ){
		    switch( action.leave( this ) ){
	            case ASTVisitor.PROCESS_ABORT : return false;
	            case ASTVisitor.PROCESS_SKIP  : return true;
	            default : break;
	        }
		}
        return true;
    }
    
    public IType getExpressionType() {
    	return CPPSemantics.VOID_TYPE;
    }
}
