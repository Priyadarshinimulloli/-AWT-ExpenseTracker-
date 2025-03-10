package awt;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.sql.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;


class  ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText((value == null) ? "Delete" : value.toString());
        return this;
    }
}

class ButtonEditorDelete extends DefaultCellEditor {
    private JButton button;
    private int selectedRow;
    private JTable table;
    private Connection conn;
    private DefaultTableModel model;
    private String tableName; // Table Name to delete from

    public ButtonEditorDelete(final JTable table, Connection conn, DefaultTableModel model, String tableName) {
        super(new JTextField());
        this.table = table;
        this.conn = conn;
        this.model = model;
        this.tableName = tableName;

        button = new JButton("Delete");
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int id = (int) table.getValueAt(selectedRow, 0); // Get ID from table
                deleteRow(id, selectedRow);
            }
        });
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        selectedRow = row;
        button.setText((value == null) ? "Delete" : value.toString());
        return button;
    }

    public Object getCellEditorValue() {
        return "Delete";
    }

    private void deleteRow(int id, int row) {
        try {
            String query = "DELETE FROM " + tableName + " WHERE id = ?";
            PreparedStatement delStmt = conn.prepareStatement(query);
            delStmt.setInt(1, id);
            delStmt.executeUpdate();

            JOptionPane.showMessageDialog(null, tableName + " record deleted successfully!");
            model.removeRow(row); // Remove row from table
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

public class FinanaceManager {
	private static Connection conn;
	private static DefaultTableModel model;
	private DefaultTableModel expmodel;
	public static void main(String[] args) throws ClassNotFoundException, SQLException,IOException {
		// TODO Auto-generated method stub
		
		try {
		Properties config = DatabaseConfig.loadConfig();
		String url = config.getProperty("DB_URL", "").trim();
		String user = config.getProperty("DB_USER", "").trim();
		String pass = config.getProperty("DB_PASS", "").trim();
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		 conn=DriverManager.getConnection(url,user,pass);
		Statement statement=conn.createStatement();
		 statement.execute("USE finanace");
         }catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		//Frontend
		final JFrame frame=new JFrame("Finance Manager");
		frame.setSize(600,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GradientPanel gradientpanel=new GradientPanel();
		gradientpanel.setLayout(new BorderLayout());
		
		JLabel title = new JLabel("Welcome To Finance Manager", SwingConstants.CENTER);
		title.setFont(new Font("Arial", Font.BOLD, 24));
		title.setForeground(Color.WHITE);
		title.setBounds(70, 50, 500, 40);
		gradientpanel.add(title, BorderLayout.NORTH);
		frame.add(gradientpanel);
		frame.setContentPane(gradientpanel);
		gradientpanel.setLayout(null);
		
	
		//Name
		JLabel name=new JLabel("Name");
		name.setBounds(30,100,100,30);
		frame.add(name);
		final JTextField namefield=new JTextField();
		namefield.setBounds(170,100,100,30);
		frame.add(namefield);
		
		//Email
		JLabel email=new JLabel("Email");
		email.setBounds(30,140,100,30);
		frame.add(email);
		final JTextField emailfield=new JTextField();
		emailfield.setBounds(170,140,100,30);
		frame.add(emailfield);
		
		//phone
		JLabel phone=new JLabel("Phone");
		phone.setBounds(30,180,100,30);
		frame.add(phone);
		final JTextField phonefield=new JTextField();
		phonefield.setBounds(170,180,100,30);
		frame.add(phonefield);
		
		//category
		JLabel category=new JLabel("Category");
		category.setBounds(30,220,100,30);
		frame.add(category);
		final JTextField catfield=new JTextField();
		catfield.setBounds(170,220,100,30);
		frame.add(catfield);
		
		//amount
		JLabel amount=new JLabel("Amount");
		amount.setBounds(30,260,100,30);
		frame.add(amount);
		final JTextField amtfield=new JTextField();
		amtfield.setBounds(170,260,100,30);
		frame.add(amtfield);
		
		//Date
		JLabel date=new JLabel("Date(YYYY-MM-DD)");
		date.setBounds(30,300,150,30);
		frame.add(date);
		final JTextField datefield=new JTextField();
		datefield.setBounds(170,300,100,30);
		frame.add(datefield);
		
		

		//submit
		JButton button=new JButton("Submit");
		button.setBounds(30,340,150,30);
		gradientpanel.add(button);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
				String name=namefield.getText();
				String email=emailfield.getText();
				String phone=phonefield.getText();
				String category=catfield.getText();
				String amount=amtfield.getText();
				String date=datefield.getText();
				
				
				if(name.isEmpty()||email.isEmpty()||phone.isEmpty()||category.isEmpty()||amount.isEmpty()||date.isEmpty()) {
					JOptionPane.showMessageDialog(frame,"Please fill all the fields!!");
					return;
				}
				
				String query="INSERT INTO CUSTOMER(name,email,phone)VALUES(?,?,?)";
				PreparedStatement preparedstatement=conn.prepareStatement(query);
				preparedstatement.setString(1, name);
				preparedstatement.setString(2,email);
				preparedstatement.setString(3, phone);
				preparedstatement.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Customer Added Successfully!");
                
                String query2="INSERT INTO EXPENSE(category,amount,date)VALUES(?,?,?)";
                PreparedStatement ps=conn.prepareStatement(query2);
				ps.setString(1,category );
				ps.setString(2,amount);
				ps.setString(3, date);
				ps.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Expense Added Successfully!");
                
				}catch(SQLException e) {
					e.printStackTrace();
				}
				
			}
		});
		//view
		JButton view=new JButton("View");
		view.setBounds(200,340,150,30);
		gradientpanel.add(view);
		
		view.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFrame tableFrame=new JFrame("📋Customer and Expense List");
				tableFrame.setSize(600,500);
				tableFrame.setLayout(new GridLayout(2,1));
				tableFrame.setLocationRelativeTo(null);;
				
				
				String[] col= {"Id","Name","Email","Phone","Delete"};
				DefaultTableModel model=new DefaultTableModel(col,0);
				JTable table=new JTable(model);
				styleTable(table);
				
				try {
					PreparedStatement statement=conn.prepareStatement("SELECT * FROM CUSTOMER");
					ResultSet res=statement.executeQuery();
					
					while(res.next()) {
							final int id=res.getInt("id");
							final String name=res.getString("name");
							final String email=res.getString("email");
							final String phone=res.getString("phone");
							
							model.addRow(new Object[] {id,name,email,phone});
						
					}
					table.getColumn("Delete").setCellRenderer(new ButtonRenderer());
					table.getColumn("Delete").setCellEditor(new ButtonEditorDelete(table, conn, model, "CUSTOMER"));

					JScrollPane scrollpane=new JScrollPane(table);
					tableFrame.add(scrollpane);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				String[] expcol = {"ID", "Category", "Amount", "Date", "Delete"};
				final DefaultTableModel expmodel = new DefaultTableModel(expcol, 0);
				final JTable table2 = new JTable(expmodel);
				styleTable(table2);
				

				try {
				    PreparedStatement statement = conn.prepareStatement("SELECT * FROM EXPENSE");
				    ResultSet res = statement.executeQuery();

				    while (res.next()) {
				        final int id = res.getInt("id");
				        final String cat = res.getString("category");
				        final String amt = res.getString("amount");
				        final String date = res.getString("date");

				        expmodel.addRow(new Object[]{id, cat, amt, date, "Delete"});
				    }

				    table2.getColumn("Delete").setCellRenderer(new ButtonRenderer());
				    table2.getColumn("Delete").setCellEditor(new ButtonEditorDelete(table2, conn, expmodel, "EXPENSE"));

				    JScrollPane sp = new JScrollPane(table2);
				    tableFrame.add(sp);

				} catch (SQLException e) {
				    e.printStackTrace();
				}

				tableFrame.setVisible(true);
				  SwingUtilities.updateComponentTreeUI(tableFrame);
			}
		});
		
		JButton Totalexpense = new JButton("TotalExpense");
		Totalexpense.setBounds(400, 340, 150, 30);
		gradientpanel.add(Totalexpense);

		Totalexpense.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            String query = "SELECT c.name, c.email, SUM(e.amount) AS total_amount " +
		                           "FROM CUSTOMER c " +
		                           "JOIN EXPENSE e ON c.id = e.id " +
		                           "GROUP BY c.name, c.email";

		            PreparedStatement pst = conn.prepareStatement(query);
		            ResultSet rs = pst.executeQuery();

		            // Create a new JFrame
		            JFrame frame = new JFrame("Total Expenses");
		            frame.setSize(400, 400);
		            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		            // Create JTextArea to display the result
		            JTextArea textArea = new JTextArea();
		            textArea.setEditable(false);
		            
		            StringBuilder result = new StringBuilder();
		            while (rs.next()) {
		                String name = rs.getString("name");
		                String email = rs.getString("email");
		                double totalAmount = rs.getDouble("total_amount");

		                result.append("Name: ").append(name)
		                      .append("\nEmail: ").append(email)
		                      .append("\nTotal Expense: ").append(totalAmount)
		                      .append("\n----------------\n");
		            }

		            if (result.length() == 0) {
		                textArea.setText("No expense records found.");
		            } else {
		                textArea.setText(result.toString());
		            }

		            // Add JTextArea to JScrollPane for scrollability
		            JScrollPane scrollPane = new JScrollPane(textArea);
		            frame.add(scrollPane);

		            // Make the frame visible
		            frame.setVisible(true);
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		    }
		});

		
		frame.setVisible(true);
   }

public static class GradientPanel extends JPanel{
	  @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g;
	        int width = getWidth();
	        int height = getHeight();

	        
	        Color color1 = new Color(34, 193, 195); 
	        Color color2 = new Color(253, 187, 45); 

	        GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
	        g2d.setPaint(gp);
	        g2d.fillRect(0, 0, width, height);
	    }
}

	private static void styleTable(JTable table) {
		table.setFont(new Font("ARIAL",Font.PLAIN,14));
		table.setRowHeight(30);
		table.setShowGrid(true);
		table.setGridColor(Color.LIGHT_GRAY);
		
		JTableHeader header=table.getTableHeader();
		 header.setFont(new Font("Arial", Font.BOLD, 15));
	    header.setForeground(Color.WHITE);
	   header.setBackground(new Color(54, 69, 79));
	}
	

}






