package com.orasio.mvc;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Created by spielerl on 29/04/2017.
 */
@RestController
public class BookController {


    @RequestMapping(value="/testStatus", method= RequestMethod.GET)
    public String testStatus() {
        return "OK";
    }

    @RequestMapping(value="/books", method= RequestMethod.POST, consumes = "application/json")
    public List<Book> createBooks(@RequestBody  List<Book> books) {
        books.forEach(book -> book.setISBN(  generateISBN() ));
        return books;
    }

    public String generateISBN() {
        return UUID.randomUUID().toString().replaceAll("/", "").substring(0,12);
    }
}
