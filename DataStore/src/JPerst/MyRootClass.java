package JPerst;

import org.garret.perst.FieldIndex;
import org.garret.perst.Index;
import org.garret.perst.Persistent;
import org.garret.perst.Storage;

public class MyRootClass extends Persistent{
	
	public FieldIndex<MyPersistentClass> intKeyIndex;

	public FieldIndex<MyPersistentClass> strKeyIndex;
	
	public Index<MyPersistentClass> idIndex;
	
	
	public MyRootClass(Storage db) {
	super(db);
	intKeyIndex = db.<MyPersistentClass>createFieldIndex(
	MyPersistentClass.class, // indexed class
	"intKey", // name of indexed field
	true); // unique index
	strKeyIndex = db.<MyPersistentClass>createFieldIndex(
	MyPersistentClass.class, // indexed class
	"strKey", // name of indexed field
	false); // index allows duplicates (is not unique)
	idIndex = db.createIndex(
	int.class, // key type
	false); // index allows duplicates (is not unique)
	}

	public MyRootClass() {}
}
