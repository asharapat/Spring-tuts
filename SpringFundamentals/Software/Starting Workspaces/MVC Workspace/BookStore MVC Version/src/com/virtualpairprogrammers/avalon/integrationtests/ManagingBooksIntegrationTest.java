package com.virtualpairprogrammers.avalon.integrationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.virtualpairprogrammers.avalon.data.BookNotFoundException;
import com.virtualpairprogrammers.avalon.domain.Book;
import com.virtualpairprogrammers.avalon.services.BookService;

/**
 * A JUnit4 test to integration test the Spring Container setup
 *
 * @author Richard Chesterwood
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( { "/other-tiers.xml", "/datasource-test.xml" } )
@Transactional
public class ManagingBooksIntegrationTest 
{	
	@Autowired
	private BookService books;
	
	@Test
	public void testFindingByIsbn()
	{
		// arrange
		String isbn = "1933988134";
		Book testBook = new Book(isbn, "Spring in Action", "Craig Walls", 23.99);
		books.registerNewBook(testBook);

		// act
		Book foundBook = null;
		try 
		{
			foundBook = books.getBookByIsbn(isbn);
			assertEquals("The book returned is the wrong one!", testBook, foundBook);
		} 
		catch (BookNotFoundException e) 
		{
			fail("No book was found when one should have been!");
		}		
	}
	
	@Test
	public void testAddingBooks()
	{
		// act
		books.registerNewBook(new Book("1933988134", "Spring in Action", "Craig Walls", 23.99));
		books.registerNewBook(new Book("0764543857", "Expert One-on-one J2EE Design and Development", "Rod Johnson", 28.59));

		// assert
		int booksInDb = books.getEntireCatalogue().size();
		assertEquals("There should be two books in the database!", 2, booksInDb);

	}
	
	@Test(expected = BookNotFoundException.class)  
	public void testFindingNonExistentBook() throws BookNotFoundException
	{
 	    Book foundBook = books.getBookByIsbn("some garbage isbn");
	}
}
