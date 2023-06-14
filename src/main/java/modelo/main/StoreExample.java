package modelo.main;

import java.io.File;

import org.exist.xmldb.EXistResource;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import util.ConnectionManager;
import util.MyDataSource;


//http://exist-db.org/exist/apps/doc/devguide_xmldb
public class StoreExample {

	/**
	 * args[0] Should be the name of the collection to access: books or books/gal
	 * args[1] Should be the name of the file to read and store in the collection
	 */
	public static void main(String args[]) throws Exception {

		if (args.length < 2) {
			System.out.println("usage: StoreExample collection-path document");
			System.exit(1);
		}

		MyDataSource dataSource = ConnectionManager.getDataSource();

		Class cl = Class.forName(dataSource.getDriver());
		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");

		DatabaseManager.registerDatabase(database);

		Collection col = null;
		XMLResource res = null;
		try {
			col = getOrCreateCollection(args[0], dataSource);

			// create new XMLResource; an id will be assigned to the new resource
			// Si se le pasa null como primer argumento, indica que se le asignará un
			// identificador cuando se cree el documento
			res = (XMLResource) col.createResource(args[1], "XMLResource");
			File f = new File(args[1]);

			if (!f.canRead()) {
				System.out.println("cannot read file " + args[1]);
				return;
			}

			res.setContent(f);

			System.out.print("storing document " + res.getId() + "...");
			col.storeResource(res);
			System.out.println("ok.");
		} finally {
			// dont forget to cleanup
			if (res != null) {
				try {
					((EXistResource) res).freeResources();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}

			if (col != null) {
				try {
					col.close();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
	}

	private static Collection getOrCreateCollection(String collectionUri, MyDataSource datasource) throws XMLDBException {
		return getOrCreateCollection(collectionUri, 0, datasource);
	}

	// método recursivo
	private static Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset, MyDataSource datasource) throws XMLDBException {

		String uri = datasource.getUrl();
		String user = datasource.getUser();
		String pwd = datasource.getPwd();

		Collection col = DatabaseManager.getCollection(uri + collectionUri, user, pwd);
		// Si la colección no existe
		if (col == null) {
			// Se elimina la / inicial de la colección
			if (collectionUri.startsWith("/")) {
				collectionUri = collectionUri.substring(1);
			}

			// Se crean segmentos separados por /
			String pathSegments[] = collectionUri.split("/");
			if (pathSegments.length > 0) {

				StringBuilder path = new StringBuilder();
				// Se crea el path paso a paso: En la primera ejecución, path solo tiene el
				// primer segmento, en la segunda ejecución el primer y segundo segmentos
				for (int i = 0; i <= pathSegmentOffset; i++) {
					path.append("/" + pathSegments[i]);
				}

				// Se intenta obtener la colección con el primer segmento (en la primera
				// llamada),
				Collection start = DatabaseManager.getCollection(uri + path, user, pwd);
				if (start == null) {
					// collection does not exist, so create
					String parentPath = path.substring(0, path.lastIndexOf("/"));
					Collection parent = DatabaseManager.getCollection(uri + parentPath, user, pwd);

					// Se crea el servicio sobre la colección padre de la que queremos crear
					CollectionManagementService mgt = (CollectionManagementService) parent
							.getService("CollectionManagementService", "1.0");

					col = mgt.createCollection(pathSegments[pathSegmentOffset]);

					col.close();
					parent.close();
				} else {
					start.close();
				}
			}
			return getOrCreateCollection(collectionUri, ++pathSegmentOffset, datasource);
		} else {
			return col;
		}
	}
}
