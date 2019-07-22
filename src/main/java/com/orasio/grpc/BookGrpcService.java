package com.orasio.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.springboot.autoconfigure.grpc.server.GrpcService;
import org.orasio.grpc.services.Book;
import org.orasio.grpc.services.BookList;
import org.orasio.grpc.services.BookServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@GrpcService(BookGrpcService.class)
public class BookGrpcService extends BookServiceGrpc.BookServiceImplBase {

    private Logger logger = LoggerFactory.getLogger(BookGrpcService.class);

    @Override
    public void createBooks(BookList request, StreamObserver<BookList> responseObserver) {
        logger.debug("Request " + request);

        BookList.Builder responseBuilder = BookList.newBuilder();

        assignISBN(request.getBookList()).forEach(responseBuilder::addBook);

        BookList bookListResponse = responseBuilder.build();

        logger.debug("Response " + bookListResponse);

        responseObserver.onNext(bookListResponse);
        responseObserver.onCompleted();
    }

    private List<Book> assignISBN(List<Book> books) {

        List<Book> result = new LinkedList<>();
        for (Book book : books) {
            Book bookWithISBN = Book.newBuilder(book)
                    .setISBN(generateISBN())
                    .build();

            result.add(bookWithISBN);
        }

        return result;
    }

    private String generateISBN() {
        return UUID.randomUUID().toString().replaceAll("/", "").substring(0,12);
    }
}
