package ca.example.e4.selection.model;

public class Album {

	private String  name;
	private boolean openable;
	
	public Album(String name, boolean openable) {
		super();
		this.name = name;
		this.openable = openable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOpenable() {
		return openable;
	}

	public void setOpenable(boolean openable) {
		this.openable = openable;
	}

	@Override
	public String toString() {
		return "Album [name=" + name + ", openable=" + openable + "]";
	}

	
	
}
