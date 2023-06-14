package modelo.servicio.book;

import java.util.List;

import modelo.Book;
import modelo.dao.book.BookEXistDao;
import modelo.dao.book.IBookDao;

public class ServicioBook implements IServicioBook {

	private IBookDao bookDao;

	public ServicioBook() {
		bookDao = new BookEXistDao();
	}

	@Override
	public List<Book> findAll() {
		return bookDao.findAll();
	}



	




}
