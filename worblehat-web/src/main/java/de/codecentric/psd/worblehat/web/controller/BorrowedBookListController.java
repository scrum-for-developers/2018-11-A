package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.BorrowedBooksFormData;
import de.codecentric.psd.worblehat.web.formdata.ReturnAllBooksFormData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

/**
 * Controller class for the book table result.
 */
@Controller
@RequestMapping("/borrowedBookList")
public class BorrowedBookListController {

	private BookService bookService;

	@Autowired
	public BorrowedBookListController(BookService bookService) {
		this.bookService = bookService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String prepareView(ModelMap modelMap) {
		modelMap.put("borrowedBooksFormData", new BorrowedBooksFormData());
		modelMap.addAttribute("books",Collections.emptyList());
		return "borrowedBookList";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String getBorrowedBooks(@ModelAttribute("borrowedBooksFormData") @Valid BorrowedBooksFormData formData,
			BindingResult result, ModelMap modelMap) {
		if (result.hasErrors()) {
			return "borrowedBookList";
		} else {
			List<Book> books = bookService.findMyBorrowedBooks(formData.getEmailAddress());
			modelMap.addAttribute("books", books);
			return "borrowedBookList";
		}
	}
}
