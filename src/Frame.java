import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {
    public Frame(String title) {
        this.setTitle(title);
        this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        this.setSize(1450, 1000);
        this.setLayout(new BorderLayout());
    }
}
