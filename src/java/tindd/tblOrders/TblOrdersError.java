/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tindd.tblOrders;

import java.io.Serializable;

/**
 *
 * @author Tin
 */
public class TblOrdersError implements Serializable {

    private String emptyDiscount;
    private String alreadyUsed;
    private String discountNotValid;
    private String expiredDiscount;
    private String deleteOrderFail;
    private String feedbackFail;
    private String starNotLessThanOne;
    private String feedbackNotAuthorized;
    private String feedbackBefore;

    public TblOrdersError() {
    }

    public TblOrdersError(String emptyDiscount, String alreadyUsed, String discountNotValid, String expiredDiscount, String deleteOrderFail, String feedbackFail, String starNotLessThanOne, String feedbackNotAuthorized, String feedbackBefore) {
        this.emptyDiscount = emptyDiscount;
        this.alreadyUsed = alreadyUsed;
        this.discountNotValid = discountNotValid;
        this.expiredDiscount = expiredDiscount;
        this.deleteOrderFail = deleteOrderFail;
        this.feedbackFail = feedbackFail;
        this.starNotLessThanOne = starNotLessThanOne;
        this.feedbackNotAuthorized = feedbackNotAuthorized;
        this.feedbackBefore = feedbackBefore;
    }

    public String getEmptyDiscount() {
        return emptyDiscount;
    }

    public void setEmptyDiscount(String emptyDiscount) {
        this.emptyDiscount = emptyDiscount;
    }

    public String getAlreadyUsed() {
        return alreadyUsed;
    }

    public void setAlreadyUsed(String alreadyUsed) {
        this.alreadyUsed = alreadyUsed;
    }

    public String getDiscountNotValid() {
        return discountNotValid;
    }

    public void setDiscountNotValid(String discountNotValid) {
        this.discountNotValid = discountNotValid;
    }

    public String getExpiredDiscount() {
        return expiredDiscount;
    }

    public void setExpiredDiscount(String expiredDiscount) {
        this.expiredDiscount = expiredDiscount;
    }

    public String getDeleteOrderFail() {
        return deleteOrderFail;
    }

    public void setDeleteOrderFail(String deleteOrderFail) {
        this.deleteOrderFail = deleteOrderFail;
    }

    public String getFeedbackFail() {
        return feedbackFail;
    }

    public void setFeedbackFail(String feedbackFail) {
        this.feedbackFail = feedbackFail;
    }

    public String getStarNotLessThanOne() {
        return starNotLessThanOne;
    }

    public void setStarNotLessThanOne(String starNotLessThanOne) {
        this.starNotLessThanOne = starNotLessThanOne;
    }

    public String getFeedbackNotAuthorized() {
        return feedbackNotAuthorized;
    }

    public void setFeedbackNotAuthorized(String feedbackNotAuthorized) {
        this.feedbackNotAuthorized = feedbackNotAuthorized;
    }

    public String getFeedbackBefore() {
        return feedbackBefore;
    }

    public void setFeedbackBefore(String feedbackBefore) {
        this.feedbackBefore = feedbackBefore;
    }

}
