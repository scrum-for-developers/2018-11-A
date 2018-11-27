package de.codecentric.worblehat.acceptancetests.step.page;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import de.codecentric.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.PageElement;
import de.codecentric.worblehat.acceptancetests.adapter.Config;
import de.codecentric.worblehat.acceptancetests.step.StoryContext;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import de.codecentric.worblehat.acceptancetests.adapter.SeleniumAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Home {

	private SeleniumAdapter seleniumAdapter;

	@Autowired
	public StoryContext context;

	@Autowired
	public Home(SeleniumAdapter seleniumAdapter) {
		this.seleniumAdapter = seleniumAdapter;
	}

	// *****************
	// *** T H E N *****
	// *****************

	@Then("the version is shown")
	public void pageShowVersion() {

		seleniumAdapter.gotoPage(Page.HOME);

		List<String> errorMsgs = seleniumAdapter.findAllStringsForElement(PageElement.VERSION);
		assertThat(errorMsgs.size(), is(1));

		assertThat(errorMsgs.get(0), is(Config.getVersion()));
		// TODO: check version number.
	}


}
