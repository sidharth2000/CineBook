/**
 * @author Sidharthan Jayavelu
 * 
 * Description:
 * 
 * This class is abstract creator for DiscountCode
 * 
 */

package com.cinebook.discountcodefactory;

import com.cinebook.model.DiscountCode;

// Creator
public abstract class DiscountCodeCreator {

	public final DiscountCode generateCode() {
		validate();
		DiscountCode code = createDiscountCode();
		postProcess(code);
		return code;
	}

	protected abstract DiscountCode createDiscountCode();

	protected void validate() {
	}

	protected void postProcess(DiscountCode code) {
	}
}
