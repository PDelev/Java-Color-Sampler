//Pavel Delev
//CS 326 HW 8
//05-04-2020

import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.event.*;

class Drawing extends JComponent{
    protected int red;
    protected int green;
    protected int blue;

    public void paint(Graphics g){
        Dimension d = getSize();

        g.setColor(new Color(red, green, blue));
        g.fillRect(1, 1, d.width-2, d.height-2);
    }

    public void repaint(Graphics g){
        Dimension d = getSize();

        g.setColor(new Color(red, green, blue));
        g.fillRect(1, 1, d.width-2, d.height-2);
    }
}

public class ColorApp extends JFrame{
    protected Drawing color_box;
    protected JLabel label_red;
    protected JLabel label_green;
    protected JLabel label_blue;
    protected JTextField tf_r_val;
    protected JTextField tf_g_val;
    protected JTextField tf_b_val;
    protected JButton r_button_plus;
    protected JButton r_button_minus;
    protected JButton g_button_plus;
    protected JButton g_button_minus;
    protected JButton b_button_plus;
    protected JButton b_button_minus;
    protected JButton button_save;
    protected JButton button_reset;
    protected JList list_colors;
    protected JPanel panel_list;

    static protected String s_colors[]= new String[11];
    static protected int[] r_vals = new int[11];
    static protected int[] g_vals = new int[11];
    static protected int[] b_vals = new int[11];

    public static void main(String[] args) throws IOException{
        int counter = 0;

        FileInputStream stream = new FileInputStream("colors.txt");
        InputStreamReader reader = new InputStreamReader(stream);
        StreamTokenizer tokens = new StreamTokenizer(reader);

        while(tokens.nextToken() != tokens.TT_EOF){
            s_colors[counter] = (String) tokens.sval;
            tokens.nextToken();
            r_vals[counter] = (int) tokens.nval;
            tokens.nextToken();
            g_vals[counter] = (int) tokens.nval;
            tokens.nextToken();
            b_vals[counter] = (int) tokens.nval;
          //  System.out.println(s_colors[counter] + " " + r_vals[counter] + " " + g_vals[counter] + " " + b_vals[counter]);
            counter++;
        }
        stream.close();

        new ColorApp("Color Sampler", s_colors);
    }

    public ColorApp(String title, String[] colors) {
        super(title);
        setBounds(100, 100, 305, 330);
        addWindowListener(new WindowDestroyer());

        color_box = new Drawing();
        label_red = new JLabel("Red:");
        label_green = new JLabel("Green:");
        label_blue = new JLabel("Blue:");
        tf_r_val = new JTextField("");
        tf_r_val.setHorizontalAlignment(SwingConstants.CENTER); //Aligns the text to the center of the text field
        tf_g_val = new JTextField("");
        tf_g_val.setHorizontalAlignment(SwingConstants.CENTER); //Aligns the text to the center of the text field
        tf_b_val = new JTextField("");
        tf_b_val.setHorizontalAlignment(SwingConstants.CENTER); //Aligns the text to the center of the text field
        r_button_plus = new JButton("+");
        r_button_minus = new JButton("-");
        g_button_plus = new JButton("+");
        g_button_minus = new JButton("-");
        b_button_plus = new JButton("+");
        b_button_minus = new JButton("-");
        button_save = new JButton("Save");
        button_reset = new JButton("Reset");
        list_colors = new JList();
        panel_list = new JPanel();

        r_button_plus.addActionListener(new ActionHandler());
        r_button_minus.addActionListener(new ActionHandler());
        g_button_plus.addActionListener(new ActionHandler());
        g_button_minus.addActionListener(new ActionHandler());
        b_button_plus.addActionListener(new ActionHandler());
        b_button_minus.addActionListener(new ActionHandler());
        button_save.addActionListener(new ActionHandler());
        button_reset.addActionListener(new ActionHandler());
        list_colors.addListSelectionListener(new ListHandler());

        panel_list.add(list_colors);

        getContentPane().add(color_box);
        getContentPane().add(label_red);
        getContentPane().add(tf_r_val);
        getContentPane().add(r_button_plus);
        getContentPane().add(r_button_minus);
        getContentPane().add(label_green);
        getContentPane().add(tf_g_val);
        getContentPane().add(g_button_plus);
        getContentPane().add(g_button_minus);
        getContentPane().add(label_blue);
        getContentPane().add(tf_b_val);
        getContentPane().add(b_button_plus);
        getContentPane().add(b_button_minus);
        getContentPane().add(button_save);
        getContentPane().add(button_reset);
        getContentPane().add(panel_list);
        getContentPane().add(list_colors);

        color_box.setBounds(10, 10, 210, 150);
        label_red.setBounds(10, 170, 50, 20);
        tf_r_val.setBounds(60, 170, 50, 20);
        r_button_minus.setBounds(120, 170, 45, 20);
        r_button_plus.setBounds(175, 170, 45, 20);
        label_green.setBounds(10, 200, 50, 20);
        tf_g_val.setBounds(60, 200, 50, 20);
        g_button_minus.setBounds(120, 200, 45, 20);
        g_button_plus.setBounds(175, 200, 45, 20);
        label_blue.setBounds(10, 230, 50, 20);
        tf_b_val.setBounds(60, 230, 50, 20);
        b_button_minus.setBounds(120, 230, 45, 20);
        b_button_plus.setBounds(175, 230, 45, 20);
        button_save.setBounds(10, 260, 100, 20);
        button_reset.setBounds(120, 260, 100, 20);

        panel_list.add(list_colors);
        panel_list.setBounds(220,5,70,210);

        setVisible(true);

        list_colors.setListData(colors);
    }

    private class ActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int color_val = 0;
            int selected_index = list_colors.getSelectedIndex();

            //r_button_plus was pressed
            if(e.getSource() == r_button_plus){
                setTitle("Color Sampler*");
                if(Integer.parseInt(tf_r_val.getText()) != 255){            //check for the value not to exceed 255
                    color_val = Integer.parseInt(tf_r_val.getText()) + 5;
                    tf_r_val.setText("" + color_val);
                    color_box.red = color_val;
                    color_box.repaint();
                }
            }

            //r_button_minus was pressed
            if(e.getSource() == r_button_minus){
                setTitle("Color Sampler*");
                if(Integer.parseInt(tf_r_val.getText()) != 0){            //check for the value not to get negative
                    color_val = Integer.parseInt(tf_r_val.getText()) - 5;
                    tf_r_val.setText("" + color_val);
                    color_box.red = color_val;
                    color_box.repaint();
                }
            }

            //g_button_plus was pressed
            if(e.getSource() == g_button_plus){
                setTitle("Color Sampler*");
                if(Integer.parseInt(tf_g_val.getText()) != 255){
                    color_val = Integer.parseInt(tf_g_val.getText()) + 5;
                    tf_g_val.setText("" + color_val);
                    color_box.green = color_val;
                    color_box.repaint();
                }
            }

            //g_button_minus was pressed
            if(e.getSource() == g_button_minus){
                setTitle("Color Sampler*");
                if(Integer.parseInt(tf_g_val.getText()) != 0){ 
                    color_val = Integer.parseInt(tf_g_val.getText()) - 5;
                    tf_g_val.setText("" + color_val);
                    color_box.green = color_val;
                    color_box.repaint();
                }
            }

            //b_button_plus was pressed
            if(e.getSource() == b_button_plus){
                setTitle("Color Sampler*");
                if(Integer.parseInt(tf_b_val.getText()) != 255){
                    color_val = Integer.parseInt(tf_b_val.getText()) + 5;
                    tf_b_val.setText("" + color_val);
                    color_box.blue = color_val;
                    color_box.repaint();
                }
            }

            //b_button_minus was pressed
            if(e.getSource() == b_button_minus){
                setTitle("Color Sampler*");
                if(Integer.parseInt(tf_b_val.getText()) != 0){ 
                    color_val = Integer.parseInt(tf_b_val.getText()) - 5;
                    tf_b_val.setText("" + color_val);
                    color_box.blue = color_val;
                    color_box.repaint();
                }
            }

            //the save button was pressed
            if(e.getSource() == button_save){
                setTitle("Color Sampler");
                saveCurrentColor();
            }

            //the reset button was pressed
            if(e.getSource() == button_reset){
                setTitle("Color Sampler");
                resetProperties();
            }
            
        }
    }

    private class ListHandler implements ListSelectionListener{
        public void valueChanged(ListSelectionEvent e) {
            if(e.getSource() == list_colors){
                if(!e.getValueIsAdjusting()){
                    setTitle("Color Sampler");
                    setProperties();
                }
            }
        }
    }

    public void saveCurrentColor() {
        int selected_index = list_colors.getSelectedIndex();

        r_vals[selected_index] = Integer.parseInt(tf_r_val.getText());
        g_vals[selected_index] = Integer.parseInt(tf_g_val.getText());
        b_vals[selected_index] = Integer.parseInt(tf_b_val.getText());
    }

    public void setProperties(){
        int selected_index = list_colors.getSelectedIndex();

        switch(selected_index){
            case 0: //red
                tf_r_val.setText("" + r_vals[0]);
                tf_g_val.setText("" + g_vals[0]);
                tf_b_val.setText("" + b_vals[0]);
                color_the_box(r_vals[0], g_vals[0], b_vals[0]);
                break;
            case 1: //green
                tf_r_val.setText("" + r_vals[1]);
                tf_g_val.setText("" + g_vals[1]);
                tf_b_val.setText("" + b_vals[1]);
                color_the_box(r_vals[1], g_vals[1], b_vals[1]);
                break;
            case 2: //blue
                tf_r_val.setText("" + r_vals[2]);
                tf_g_val.setText("" + g_vals[2]);
                tf_b_val.setText("" + b_vals[2]);
                color_the_box(r_vals[2], g_vals[2], b_vals[2]);
                break;
            case 3: //yellow
                tf_r_val.setText("" + r_vals[3]);
                tf_g_val.setText("" + g_vals[3]);
                tf_b_val.setText("" + b_vals[3]);
                color_the_box(r_vals[3], g_vals[3], b_vals[3]);
                break;
            case 4: //cyan
                tf_r_val.setText("" + r_vals[4]);
                tf_g_val.setText("" + g_vals[4]);
                tf_b_val.setText("" + b_vals[4]);
                color_the_box(r_vals[4], g_vals[4], b_vals[4]);
                break;
            case 5: //magenta
                tf_r_val.setText("" + r_vals[5]);
                tf_g_val.setText("" + g_vals[5]);
                tf_b_val.setText("" + b_vals[5]);
                color_the_box(r_vals[5], g_vals[5], b_vals[5]);
                break;
            case 6: //orange
                tf_r_val.setText("" + r_vals[6]);
                tf_g_val.setText("" + g_vals[6]);
                tf_b_val.setText("" + b_vals[6]);
                color_the_box(r_vals[6], g_vals[6], b_vals[6]);
                break;
            case 7: //pink
                tf_r_val.setText("" + r_vals[7]);
                tf_g_val.setText("" + g_vals[7]);
                tf_b_val.setText("" + b_vals[7]);
                color_the_box(r_vals[7], g_vals[7], b_vals[7]);
                break;
            case 8: //grey
                tf_r_val.setText("" + r_vals[8]);
                tf_g_val.setText("" + g_vals[8]);
                tf_b_val.setText("" + b_vals[8]);
                color_the_box(r_vals[8], g_vals[8], b_vals[8]);
                break;
            case 9: //black
                tf_r_val.setText("" + r_vals[9]);
                tf_g_val.setText("" + g_vals[9]);
                tf_b_val.setText("" + b_vals[9]);
                color_the_box(r_vals[9], g_vals[9], b_vals[9]);
                break;
            case 10: //white
                tf_r_val.setText("" + r_vals[10]);
                tf_g_val.setText("" + g_vals[10]);
                tf_b_val.setText("" + b_vals[10]);
                color_the_box(r_vals[10], g_vals[10], b_vals[10]);
                break;
        }
    
    }

    public void resetProperties(){
        int selected_index = list_colors.getSelectedIndex();

        switch(selected_index){
            case 0: //red
                r_vals[selected_index] = 255;
                g_vals[selected_index] = 0;
                b_vals[selected_index] = 0;
                tf_r_val.setText("255");
                tf_g_val.setText("0");
                tf_b_val.setText("0");
                color_the_box(255, 0, 0);
                break;
            case 1: //green
                r_vals[selected_index] = 0;
                g_vals[selected_index] = 255;
                b_vals[selected_index] = 0;
                tf_r_val.setText("0");
                tf_g_val.setText("255");
                tf_b_val.setText("0");
                color_the_box(0, 255, 0);
                break;
            case 2: //blue
                r_vals[selected_index] = 0;
                g_vals[selected_index] = 0;
                b_vals[selected_index] = 255;
                tf_r_val.setText("0");
                tf_g_val.setText("0");
                tf_b_val.setText("255");
                color_the_box(0, 0, 255);
                break;
            case 3: //yellow
                r_vals[selected_index] = 255;
                g_vals[selected_index] = 255;
                b_vals[selected_index] = 0;
                tf_r_val.setText("255");
                tf_g_val.setText("255");
                tf_b_val.setText("0");
                color_the_box(255, 255, 0);
                break;
            case 4: //cyan
                r_vals[selected_index] = 0;
                g_vals[selected_index] = 255;
                b_vals[selected_index] = 255;
                tf_r_val.setText("0");
                tf_g_val.setText("255");
                tf_b_val.setText("255");
                color_the_box(0, 255, 255);
                break;
            case 5: //magenta
                r_vals[selected_index] = 255;
                g_vals[selected_index] = 0;
                b_vals[selected_index] = 255;
                tf_r_val.setText("255");
                tf_g_val.setText("0");
                tf_b_val.setText("255");
                color_the_box(255, 0, 255);
                break;
            case 6: //orange
                r_vals[selected_index] = 255;
                g_vals[selected_index] = 100;
                b_vals[selected_index] = 0;
                tf_r_val.setText("255");
                tf_g_val.setText("100");
                tf_b_val.setText("0");
                color_the_box(255, 100, 0);
                break;
            case 7: //pink
                r_vals[selected_index] = 255;
                g_vals[selected_index] = 20;
                b_vals[selected_index] = 145;
                tf_r_val.setText("255");
                tf_g_val.setText("20");
                tf_b_val.setText("145");
                color_the_box(255, 20, 145);
                break;
            case 8: //grey
                r_vals[selected_index] = 125;
                g_vals[selected_index] = 125;
                b_vals[selected_index] = 125;
                tf_r_val.setText("125");
                tf_g_val.setText("125");
                tf_b_val.setText("125");
                color_the_box(125, 125, 125);
                break;
            case 9: //black
                r_vals[selected_index] = 0;
                g_vals[selected_index] = 0;
                b_vals[selected_index] = 0;
                tf_r_val.setText("0");
                tf_g_val.setText("0");
                tf_b_val.setText("0");
                color_the_box(0, 0, 0);
                break;
            case 10: //white
                r_vals[selected_index] = 255;
                g_vals[selected_index] = 255;
                b_vals[selected_index] = 255;
                tf_r_val.setText("255");
                tf_g_val.setText("255");
                tf_b_val.setText("255");
                color_the_box(255, 255, 255);
                break;
        }
        
    }

    public void color_the_box(int r, int g, int b){
        color_box.red = r;
        color_box.green = g;
        color_box.blue = b;
        color_box.repaint();
    }

    public void WriteToFile() {
        PrintWriter outfile = null;
        File f = new File("colors.txt");
        FileWriter fw = null;
        
        try{
            fw = new FileWriter(f);
        }
        catch (IOException e){
            System.out.println("FILE ERROR");
            System.exit(1);
        }

        outfile = new PrintWriter(fw);

        for(int i = 0; i < 11; i++){
            outfile.println(s_colors[i] + " " + r_vals[i] + " " + g_vals[i] + " " + b_vals[i]);
        }
    
        outfile.close();

    }

    private class WindowDestroyer extends WindowAdapter{
        public void windowClosing(WindowEvent e){
            WriteToFile();
            System.exit(0);
        }
    }
}