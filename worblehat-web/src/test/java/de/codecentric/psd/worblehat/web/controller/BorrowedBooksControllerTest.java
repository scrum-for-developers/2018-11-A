package de.codecentric.psd.worblehat.web.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.BorrowedBooksFormData;
import de.codecentric.psd.worblehat.web.formdata.ReturnAllBooksFormData;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;


public class BorrowedBooksControllerTest {
	private static final String BORROWER = "someone@codecentric.de";

    private BorrowedBookListController returnAllBooksController;

    private BookService bookService;

    private BorrowedBooksFormData returnAllBooksFormData;

    private BindingResult bindingResult;
    
    private Book aBook;

    @Before
    public void setUp() throws Exception {
        bookService = mock(BookService.class);
        returnAllBooksController = new BorrowedBookListController(bookService);
        returnAllBooksFormData = new BorrowedBooksFormData();
        bindingResult = new MapBindingResult(new HashMap<>(), "");
        aBook=mock(Book.class);
        when(bookService.findMyBorrowedBooks(BORROWER)).thenReturn(Arrays.asList(aBook));
    }

    @Test
    public void shouldSetupForm() throws Exception {
        ModelMap modelMap = new ModelMap();

        returnAllBooksController.prepareView(modelMap);

        assertThat(modelMap.get("borrowedBooksFormData"), is(not(nullValue())));
        assertThat(modelMap.get("books"), is(not(nullValue())));
    }

    @Test
    public void shouldRejectErrors() throws Exception {
        bindingResult.addError(new ObjectError("", ""));
        ModelMap modelMap = new ModelMap();
        
        String navigateTo = returnAllBooksController.getBorrowedBooks(returnAllBooksFormData, bindingResult,modelMap);

        assertThat(navigateTo, is("borrowedBookList"));
    }

    @SuppressWarnings("unchecked")
	@Test
    public void shouldShowBorrowedBooks() throws Exception {
    	ModelMap modelMap = new ModelMap();
        returnAllBooksFormData.setEmailAddress(BORROWER);

        String navigateTo = returnAllBooksController.getBorrowedBooks(returnAllBooksFormData, bindingResult,modelMap);

        verify(bookService).findMyBorrowedBooks(BORROWER);
        assertThat(navigateTo, is("borrowedBookList"));
        Object object = modelMap.get("books");
        assertThat(object,IsInstanceOf.instanceOf(List.class));
        List<Book> borrowedBooks=(List<Book>) object;
        assertThat(borrowedBooks.size(),is(1));
        assertThat(borrowedBooks.get(0),is(aBook));
    }
}
