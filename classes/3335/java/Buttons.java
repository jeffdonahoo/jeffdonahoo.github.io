import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class test {
  public static void make(final String[] list, Container c) {
    for (int i=0; i < list.length; i++) {
      final int x = i;
      JButton b = new JButton(list[i]);
      b.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          System.out.println(list[x]);
        }
      });
      c.add(b);
    }
  }

  public static void main(String[] args) {
    String list[] = {"Mom", "Dad", "Brother"};
    JFrame f = new JFrame();
    f.getContentPane().setLayout(new FlowLayout());
    f.setSize(500, 500);
    make(list, f.getContentPane());
    f.setVisible(true);
  }
}
