import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Category  extends  JFrame{
    private JTextField txtName;
    private JTextField txtDescription;
    private JButton btnSave;
    private JTable tblCategories;
    private JButton btnLoad;
    private JPanel pnlMain;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JTextField txtId;
    private JLabel Id;
    private JButton btnUpdate;
    private JButton btnDelete;
    Statement statement = null; // query statement
    ResultSet resultSet = null; // manages results
    String DATABASE_URL = "jdbc:sqlserver://localhost;encrypt=true;trustServerCertificate=true;databaseName=store;";
    Connection connection=null;
    public void Connect()
    {

        try {
            connection = DriverManager.getConnection(DATABASE_URL, "sa", "123456");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public  void Load()
    {
        try {
            var statement= connection.prepareStatement("select * from categories");
            ResultSet result=statement.executeQuery();
            ResultSetMetaData metaData=result.getMetaData();
            DefaultTableModel model = (DefaultTableModel) tblCategories.getModel();
            int cols=metaData.getColumnCount();
            String[] columns=new String[cols];
            for(int i=0; i<cols;i++)
                columns[i]=metaData.getColumnName(i+1);
            model.setColumnIdentifiers(columns);

            while (result.next())
            {
                var id=result.getString(1);
                var name=result.getString(2);
                var description=result.getString(3);
                String[] row={id,name,description};
                model.addRow(row);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  void Search()
    {
        try {
            var statement= connection.prepareStatement("select * from categories where id=?");
            statement.setString(1,txtSearch.getText());
            ResultSet result=statement.executeQuery();


            while (result.next()==true)
            {
                var id=result.getString(1);
                var name=result.getString(2);
                var description=result.getString(3);
                txtId.setText(id);
                txtName.setText(name);
                txtDescription.setText(description);


            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Category() {
        setContentPane(pnlMain);
        Connect();
    btnSave.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name,description;
            name=txtName.getText();

            description=txtDescription.getText();
            try {
                PreparedStatement  state=connection.prepareStatement("insert into Categories (CategoryName,Description) values (?,?)");
                state.setString(1,name);
                state.setString(2,description);
                state.executeUpdate();
                JOptionPane.showMessageDialog(null,"Record Added");
                txtName.setText("");
                txtDescription.setText("");
                txtName.requestFocus();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    });
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Load();
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Search();
            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name,description,id;
                name=txtName.getText();
                description=txtDescription.getText();
                id=txtId.getText();

                try {
                    PreparedStatement  state=connection.prepareStatement("update  Categories set CategoryName=?,Description=? where id=? ");
                    state.setString(1,name);
                    state.setString(2,description);
                    state.setString(3,id);
                    state.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record Updated");
                    txtName.setText("");
                    txtDescription.setText("");
                    txtName.requestFocus();
                    Load();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id=txtId.getText();
                try {
                    PreparedStatement  state=connection.prepareStatement("delete from  Categories  where id=? ");
                    state.setString(1,id);
                     state.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Record deleted");
                    txtName.setText("");
                    txtDescription.setText("");
                    txtName.requestFocus();
                    Load();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
