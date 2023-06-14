package modelo.main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import modelo.Book;
import modelo.dao.book.BookEXistDao;
import modelo.exceptions.InstanceNotFoundException;
import modelo.servicio.book.IServicioBook;
import modelo.servicio.book.ServicioBook;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class BookWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextArea mensajes_text_Area;
	private JList<Book> JListBooks;

	private IServicioBook bookServicio;
	private JButton btnFindAllBooks;
	private JButton btnEliminarLibro;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookWindow frame = new BookWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BookWindow() {

		bookServicio = new ServicioBook();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 847, 772);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(8, 8, 821, 500);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(19, 264, 669, 228);
		panel.add(scrollPane);

		mensajes_text_Area = new JTextArea();
		scrollPane.setViewportView(mensajes_text_Area);
		mensajes_text_Area.setEditable(false);
		mensajes_text_Area.setText("Panel de mensajes");
		mensajes_text_Area.setForeground(new Color(255, 0, 0));
		mensajes_text_Area.setFont(new Font("Monospaced", Font.PLAIN, 13));

		JListBooks = new JList<Book>();

		JListBooks.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {

				if (!e.getValueIsAdjusting()) {
					int index = JListBooks.getSelectedIndex();
					if (index != -1) {
						if(index<JListBooks.getModel().getSize()) {
						Book book = JListBooks.getModel().getElementAt(index);
						addMensaje(true, "Se ha seleccionado el book: " + book);
						}
					}
					btnEliminarLibro.setEnabled(index != -1);

				}
			}
		});

		JListBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JListBooks.setBounds(403, 37, 377, 200);

		JScrollPane scrollPanel_in_JlistAllDepts = new JScrollPane(JListBooks);
		scrollPanel_in_JlistAllDepts.setLocation(300, 0);
		scrollPanel_in_JlistAllDepts.setSize(500, 250);

		panel.add(scrollPanel_in_JlistAllDepts);

		btnFindAllBooks = new JButton("Mostrar todos los libros");
		btnFindAllBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getAllBooks();
			}
		});
		btnFindAllBooks.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnFindAllBooks.setBounds(0, 18, 284, 39);
		panel.add(btnFindAllBooks);

		btnEliminarLibro = new JButton("Eliminar libro\r\n");
		btnEliminarLibro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = JListBooks.getSelectedIndex();
				if (index != -1) {
					Book book = JListBooks.getModel().getElementAt(index);
					addMensaje(true, "Implementa aquí el método delete: " + book);

					
				}
			}
		});
		btnEliminarLibro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEliminarLibro.setBounds(0, 81, 284, 39);
		btnEliminarLibro.setEnabled(false);
		panel.add(btnEliminarLibro);

	}

	private void clearJListModel() {
		ListModel<Book> model = JListBooks.getModel();
		if (model != null) {
			if (model instanceof DefaultListModel) {
				((DefaultListModel<Book>) model).removeAllElements();
			}
		}
	}

	private void addMensaje(boolean keepText, String msg) {
		String oldText = "";
		if (keepText) {
			oldText = mensajes_text_Area.getText();

		}
		oldText = oldText + "\n" + msg;
		mensajes_text_Area.setText(oldText);

	}

	private void getAllBooks() {
		try {
			addMensaje(true, "Buscando...");
			List<Book> books = bookServicio.findAll();
			DefaultListModel<Book> defModel = new DefaultListModel<>();
			for (Book book : books) {
				defModel.addElement(book);
			}

			JListBooks.setModel(defModel);
		} catch (Exception ex) {
			ex.printStackTrace();
			clearJListModel();

			addMensaje(true, "Ha ocurrido un problema y no se han podido recuperar los libros");
		}
		
		//Para probar de forma rápida que read funciona. En realidad, debería hacerse a través del servicio.
//		BookEXistDao dao = new BookEXistDao();
//		try {
//			Book book =dao.read(1);
//			System.out.println(book);
//		} catch (InstanceNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
