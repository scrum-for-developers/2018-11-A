package de.codecentric.worblehat.acceptancetests.step.page;

import de.codecentric.worblehat.acceptancetests.adapter.wrapper.HtmlBook;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.HtmlBookList;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.worblehat.acceptancetests.adapter.SeleniumAdapter;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.PageElement;
import de.codecentric.worblehat.acceptancetests.step.business.DemoBookFactory;
import de.codecentric.psd.worblehat.domain.Book;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Component("BorrowedBookList")
public class BorrowedBookList {

	private SeleniumAdapter seleniumAdapter;

	@Autowired
	public BorrowedBookList(SeleniumAdapter seleniumAdapter) {
		this.seleniumAdapter = seleniumAdapter;
	}

	@Then("the borrowed book list for user $borrower contains a book with isbn $isbn")
	public void bookListContainsRowWithValues(final String borrower, final String isbn) {
		if (borrower == null || borrower.isEmpty() || isbn == null || isbn.isEmpty())
			return;
		seleniumAdapter.gotoPage(Page.BORROWEDBOOKLIST);
		seleniumAdapter.typeIntoField("emailAddress", borrower);
		seleniumAdapter.clickOnPageElement(PageElement.SHOWMYBORROWEDBOOKS);
		HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
		for (String i : isbn.split(" ")) {
			HtmlBook htmlBook = htmlBookList.getBookByIsbn(i);
			assertThat(htmlBook, is(not(nullValue())));
			assertThat(i, is(htmlBook.getIsbn()));
		}
	}

	@Then("the borrowed book list for user $borrower is empty")
	public void libraryIsEmpty(final String borrower) {
		if (borrower == null || borrower.isEmpty())
			return;
		seleniumAdapter.gotoPage(Page.BORROWEDBOOKLIST);
		seleniumAdapter.typeIntoField("emailAddress", borrower);
		seleniumAdapter.clickOnPageElement(PageElement.SHOWMYBORROWEDBOOKS);
		assertThat(seleniumAdapter.isNotShown(PageElement.BOOKLIST), is(true));
	}

}
