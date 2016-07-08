 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package borwellapplication;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
/**
 *
 * @author Peni
 */
public class BorwellApplication extends JFrame{
    
        //declare components of window
        private final JPanel southPanel;
        private final JPanel unitsPanel;
        private final JPanel roomDimensionsPanel;
        private final JPanel doorPanel;
        private final JPanel buttonsPanel;
        private final JTextArea messageArea;
        public JTextField roomHeightText;
        public JTextField roomWidthText;
        public JTextField roomLengthText;
        public JTextField doorWidthText;
        public JTextField doorHeightText;
        public JRadioButton metreButton;
        public JRadioButton feetButton;


        public BorwellApplication() {
            //Layout manager for frame
            setLayout(new BorderLayout());

            //Initialise units panel
            unitsPanel = new JPanel();
              unitsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Units:"));
              unitsPanel.setPreferredSize(new Dimension(400, 60));
            add(unitsPanel, BorderLayout.NORTH);

            //initialise radio buttons within units panel
            metreButton = new JRadioButton("Metres");
            metreButton.setSelected(true);
            feetButton = new JRadioButton("Feet");
            ButtonGroup radioButtonGroup = new ButtonGroup();
            radioButtonGroup.add(metreButton);
            radioButtonGroup.add(feetButton);
            unitsPanel.add(metreButton);
            unitsPanel.add(feetButton);

            //initialise room dimensions panel
            roomDimensionsPanel = new JPanel();
              roomDimensionsPanel.setLayout(new GridLayout(3, 2));
              roomDimensionsPanel.setBorder(new TitledBorder(new EtchedBorder(), "Room Dimensions:"));
              roomDimensionsPanel.setPreferredSize(new Dimension(200, 90));
            add(roomDimensionsPanel, BorderLayout.LINE_START);

            //draw labels
            JLabel roomHeightLabel = new JLabel("Height:");
            JLabel roomWidthLabel = new JLabel("Width:");
            JLabel roomLengthLabel = new JLabel("Length:");
            
            //draw text fields
            roomHeightText = new JTextField(5);
            roomWidthText = new JTextField(5);
            roomLengthText = new JTextField(5);

            //add labels and text fields to room dimensions panel
            roomDimensionsPanel.add(roomHeightLabel);
            roomDimensionsPanel.add(roomHeightText);
            roomDimensionsPanel.add(roomWidthLabel);
            roomDimensionsPanel.add(roomWidthText);
            roomDimensionsPanel.add(roomLengthLabel);
            roomDimensionsPanel.add(roomLengthText);

            //initialise door panel
            doorPanel = new JPanel();
              doorPanel.setLayout(new GridLayout(3, 2));
              doorPanel.setBorder(new TitledBorder(new EtchedBorder(), "Door:"));
              doorPanel.setPreferredSize(new Dimension(200, 60));
            add(doorPanel, BorderLayout.LINE_END);

            //draw labels
            JLabel doorWidthLabel = new JLabel("Width:");
            JLabel doorHeightLabel = new JLabel("Height:");
            
            //draw text fields
            doorWidthText = new JTextField(10);
            doorHeightText = new JTextField(10);
            
            //add labels and text fields to door panel
            doorPanel.add(doorWidthLabel);
            doorPanel.add(doorWidthText);
            doorPanel.add(doorHeightLabel);
            doorPanel.add(doorHeightText);

            //initialise south panel
            southPanel = new JPanel();
              southPanel.setPreferredSize(new Dimension(400, 130));
            add(southPanel, BorderLayout.SOUTH);

            //initialise buttons panel in south panel
            buttonsPanel = new JPanel();
              buttonsPanel.setPreferredSize(new Dimension(400, 40));
            southPanel.add(buttonsPanel, BorderLayout.PAGE_START);

            //draw cancel button
            JButton cancelButton = new JButton("Clear");
            //add action listener to cancel button
            cancelButton.addActionListener(new CancelButtonListener());
            //draw submit button
            JButton submitButton = new JButton("Submit");
            //add action listener to submit button
            submitButton.addActionListener(new SubmitButtonListener());
            
            //add buttons to button panel
            buttonsPanel.add(cancelButton, BorderLayout.CENTER);
            buttonsPanel.add(submitButton, BorderLayout.PAGE_END);

            //initialise message area in south panel
            messageArea = new JTextArea();
              //unable to edit text within text area
              messageArea.setEditable(false);
              messageArea.setPreferredSize(new Dimension(400, 80));
              messageArea.setBorder(new TitledBorder(new EtchedBorder(), "Results"));
            southPanel.add(messageArea, BorderLayout.PAGE_END);

            //program exits when window is closed
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            pack();
            setVisible(true);

        }

        //action listener for submit button
        class SubmitButtonListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent event)
            {
                //initialise dimension variables
                float roomHeight = 0;
                float roomWidth = 0;
                float roomLength = 0;
                float doorWidth = 0;
                float doorHeight = 0;
                String units = null;

                try {
                    //get text from all text boxes and parse into float
                    roomHeight = Float.parseFloat(roomHeightText.getText());
                    roomWidth = Float.parseFloat(roomWidthText.getText());
                    roomLength = Float.parseFloat(roomLengthText.getText());
                    doorWidth = Float.parseFloat(doorWidthText.getText());
                    doorHeight = Float.parseFloat(doorHeightText.getText());

                //call method to calculate volume
                float roomVolume = calculateVolume(roomHeight, roomWidth, roomLength);
                //call method to calculate area of floor
                float floorArea = calculateFloorArea(roomWidth, roomLength);
                //call method to calculate amount of paint needed
                float paintRequired = calculatePaint(roomHeight, roomWidth, roomLength, doorWidth, doorHeight);

                //is metre radio button is selected?
                if(metreButton.isSelected()) {
                    //set units string to metres
                    units = " metres";
                }
                //is feet button selected?
                else if(feetButton.isSelected()){
                    //set units string to feet
                    units = " feet";
                }
                //output all results to message area
                messageArea.setText("Volume of room: " + roomVolume + units + "\n Area of room: " + floorArea + units +"\n Amount of paint required: " + paintRequired + " litres");
                }
                //if NumberFormatException
                catch(NumberFormatException e) {
                    //output error message to message area
                    messageArea.setText("Please enter numbers.");
                }
            }
        }
        
        //action listener for cancel button
        class CancelButtonListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                //set text in all textfields to null
                roomHeightText.setText(null);
                roomLengthText.setText(null);
                roomWidthText.setText(null);
                doorHeightText.setText(null);
                doorWidthText.setText(null);
                messageArea.setText(null);
            }
        }

        //method to calculate volume of room
        public float calculateVolume(float height, float width, float length) {
            //volume = width * length * height
            float volume = width * length * height;
            return volume;
        }

        //method to calculate area of floor
        public float calculateFloorArea(float width, float length) {
            //area = length * width
            float area = length * width;
            return area;
        }

        //method to calculate amount of paint needed
        public float calculatePaint(float height, float width, float length, float dheight, float dwidth) {
            float paint = 0;
            //area of walls = (area of walls along width)*2 + (area of walls along length)*2 - area of door
            float wallArea = (((height*width)*2)+((height*length)*2)-(dheight*dwidth));
            if(metreButton.isSelected()) {
                //1 litre of paint covers 10m^2 of wall
                paint = wallArea/10;
            }
            else if(feetButton.isSelected()) {
                //1 litre of paint covers 107.6 feet^2 of wall
                paint = wallArea/(float)107.6;
            }

            return paint;
        }

    public static void main(String[] args) {
        BorwellApplication inputWindow = new BorwellApplication();
    }
}

