package model;

import java.sql.Timestamp;

public class Loan {

    private int loanId;
    private int memberId;
    private int bookId;
    private Timestamp loanDate;
    private Timestamp returnDate;

    public Loan(
            int loanId,
            int memberId,
            int bookId,
            Timestamp loanDate,
            Timestamp returnDate
    ) {

        this.loanId = loanId;
        this.memberId = memberId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public int getLoanId() {

        return loanId;
    }

    public int getMemberId() {

        return memberId;
    }

    public int getBookId() {

        return bookId;
    }

    public Timestamp getLoanDate() {

        return loanDate;
    }

    public Timestamp getReturnDate() {

        return returnDate;
    }

    public void setLoanId(int loanId) {

        this.loanId = loanId;
    }

    public void setMemberId(int memberId) {

        this.memberId = memberId;
    }

    public void setBookId(int bookId) {

        this.bookId = bookId;
    }

    public void setLoanDate(Timestamp loanDate) {

        this.loanDate = loanDate;
    }

    public void setReturnDate(Timestamp returnDate) {

        this.returnDate = returnDate;
    }

    @Override
    public String toString() {

        return "Loan{" +
                "loanId=" + loanId +
                ", memberId=" + memberId +
                ", bookId=" + bookId +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                '}';
    }
}