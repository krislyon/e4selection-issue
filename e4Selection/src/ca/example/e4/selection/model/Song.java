package ca.example.e4.selection.model;

public class Song {

	private String name;
	
	public Song(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Song [name=" + name + "]";
	}
	
	
	
}
