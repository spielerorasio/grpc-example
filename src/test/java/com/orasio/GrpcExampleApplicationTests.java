package com.orasio;

import io.grpc.Channel;
import net.devh.springboot.autoconfigure.grpc.client.GrpcClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.orasio.grpc.services.Book;
import org.orasio.grpc.services.BookList;
import org.orasio.grpc.services.BookServiceGrpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GrpcExampleApplicationTests {

	@GrpcClient("local-grpc-server")
	private Channel serverChannel;


	@Test
	public void contextLoads() {

		String body = new RestTemplate().getForObject("http://localhost:9081/testStatus", String.class);
		assertThat(body).isEqualTo("OK");

	}

	public BookList createBooks(List<Book> bookList) {
		BookServiceGrpc.BookServiceBlockingStub bookServiceBlockingStub = BookServiceGrpc.newBlockingStub(serverChannel);
		BookList.Builder builder = BookList.newBuilder();
		bookList.forEach(builder::addBook);
		BookList request = builder.build();

		BookList response = bookServiceBlockingStub.createBooks(request);
		return response;

	}


}
