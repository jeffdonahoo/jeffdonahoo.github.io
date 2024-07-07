import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class WidgetCopyMain {
	public static void main(String[] args) {
		Widget3 w1 = new Widget3("Thing1", 2.00);
		Widget3 w2 = new Widget3(w1);
		w2.setName("New Thing");
		w2.setPrice(3);
		w2.getLastChecked().setTime(5000);
		w2.getBoundingBox().get(1).setLocation(2, 2);
		System.out.println(w1);
		System.out.println(w2);
		DangerousWidget dw = new DangerousWidget("Dynamite", 4, "Do not light");
		System.out.println(dw);
		Widget3 dw2 = new Widget3(dw);
		System.out.println("Copy Constructor- " + dw2);
		dw2 = Widget3.newWidget(dw);
		System.out.println("Copy Factory- " + dw2);
		dw2 = dw.clone();
		System.out.println("Clone- " + dw2);
	}
}

class Widget3 implements Cloneable {

	protected static int NEXTID = 0;

	protected int id;
	protected String name;
	protected double price;
	protected Date lastChecked;
	protected Date created;
	protected List<Point> boundingBox = new ArrayList<>();

	public Widget3(final String name, final double price) {
		this.id = NEXTID++;
		setName(name);
		setPrice(price);
		this.lastChecked = new Date();
		this.created = new Date();
		this.boundingBox.addAll(Arrays.asList(new Point[] { new Point(0, 0),
				new Point(1, 1) }));
	}

	public Widget3(final Widget3 w) {
		this(w.name, w.price);
		this.lastChecked = (Date) w.lastChecked.clone();
		this.created = (Date) w.created.clone();
		this.boundingBox.clear();
		for (Point p : w.boundingBox) {
			this.boundingBox.add(new Point(p));
		}
	}

	public static Widget3 newWidget(Widget3 w) {
		Widget3 lw = new Widget3(w.name, w.price);
		lw.lastChecked = (Date) w.lastChecked.clone();
		lw.created = (Date) w.created.clone();
		lw.boundingBox.clear();
		for (Point p : w.boundingBox) {
			lw.boundingBox.add(new Point(p));
		}
		return lw;
	}

	@Override
	public Widget3 clone() {
		try {
			Widget3 w = (Widget3) super.clone();
			w.lastChecked = (Date) this.lastChecked.clone();
			w.created = (Date) this.created.clone();
			w.boundingBox = new ArrayList<>(this.boundingBox.size());
			for (Point p : this.boundingBox) {
				w.boundingBox.add((Point) p.clone());
			}
			return w;
		} catch (CloneNotSupportedException e) {
			// Cannot happen
			throw new AssertionError("Impossible exception thrown", e);
		}
	}

	public void setName(final String name) {
		this.name = name;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	public Date getLastChecked() {
		return lastChecked;
	}

	public List<Point> getBoundingBox() {
		return boundingBox;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder("");
		s.append(id + ": " + name + " for $" + price);
		s.append(" created on " + created + " and last certified on "
				+ lastChecked);
		s.append(" bounded by");
		for (Point p : boundingBox) {
			s.append(" (" + p.getX() + ", " + p.getY() + ") ");
		}

		return s.toString();
	}
}

class DangerousWidget extends Widget3 {

	protected String warning = "No warning";
	protected String call = "911";

	public DangerousWidget(final String name, final double price,
			final String warning) {
		super(name, price);
		this.warning = warning;
	}

	@Override
	public void setName(final String name) {
		this.name = warning + ": " + name;
	}

	@Override
	public String toString() {
		return super.toString() + " " + warning;
	}
}