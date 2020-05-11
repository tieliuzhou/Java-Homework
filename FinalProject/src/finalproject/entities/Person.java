package finalproject.entities;

public class Person implements java.io.Serializable {

	private static final long serialVersionUID = 4190276780070819093L;

	// this is a person object that you will construct with data from the DB
	// table. The "sent" column is unnecessary. It's just a person with
	// a first name, last name, age, city, and ID.
	private String first;
	private String last;
	private int age;
	private String city;
	private int id;

	public Person(String f, String l, int a, String c, int i){
		this.first = f;
		this.last = l;
		this.age = a;
		this.city = c;
		this.id = i;
	}
	public Person(Person s){
		this.first = s.first;
		this.last = s.last;
		this.age = s.age;
		this.city = s.city;
		this.id = s.id;
	}

	public String getFirst(){
		return this.first;
	}
	public String getLast(){
		return this.last;
	}
	public int getAge(){
		return this.age;
	}
	public String getCity(){
		return this.city;
	}
	public int getId(){
		return this.id;
	}


	@Override
	public String toString() {
		return "Person[" +
				"first='" + first + '\'' +
				", last='" + last + '\'' +
				", age=" + age +
				", city='" + city + '\'' +
				", id=" + id +
				']';
	}
}
