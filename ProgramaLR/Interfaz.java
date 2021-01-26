package ProgramaLR;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.Font;

public class Interfaz extends JFrame {
	
	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	AnalisisLR LR;
	String columnas[] = {"id","int","float","char",",",";","+","-","*","/","(",")","=","$","P","Tipo","V","A","E","T","F"};
	int nfilas = 32;
	String filas[];
	String NTProducciones[] = {"P'","P","P","Tipo","Tipo","Tipo","V","V","A","E","E","E","T","T","T","F","F"};
	String producciones[] = {"P","Tipo id V","A","int","float","char",", id V","; P","id = E ;","E + T","E - T","T","T * F","T / F","F","( E )","id"};
	DefaultTableModel modelo;
	DefaultTableModel modeloLexico;
	
	String mat[][] = {{"I7" ,"I4","I5","I6",null ,null ,null ,null ,null ,null ,null ,null ,null,null,"I1" ,"I2",null ,"I3",null ,null ,null },
				      {null ,null,null,null,null ,null ,null ,null ,null ,null ,null ,null ,null,"P0",null ,null,null ,null,null ,null ,null },
				      {"I8" ,null,null,null,null ,null ,null ,null ,null ,null ,null ,null ,null,null,null ,null,null ,null,null ,null ,null },
				      {null ,null,null,null,null ,null ,null ,null ,null ,null ,null ,null ,null,"P2",null ,null,null ,null,null ,null ,null },
				      {"P3" ,null,null,null,null ,null ,null ,null ,null ,null ,null ,null ,null,null,null ,null,null ,null,null ,null ,null },
				      {"P4" ,null,null,null,null ,null ,null ,null ,null ,null ,null ,null ,null,null,null ,null,null ,null,null ,null ,null },
				      {"P5" ,null,null,null,null ,null ,null ,null ,null ,null ,null ,null ,null,null,null ,null,null ,null,null ,null ,null },
				      {null ,null,null,null,null ,null ,null ,null ,null ,null ,null ,null ,"I9",null,null ,null,null ,null,null ,null ,null },
				      {null ,null,null,null,"I11","I12",null ,null ,null ,null ,null ,null ,null,null,null ,null,"I10",null,null ,null ,null },
				      {"I17",null,null,null,null ,null ,null ,null ,null ,null ,"I16",null ,null,null,null ,null,null ,null,"I13","I14","I15"},
				      {null ,null,null,null,null ,null ,null ,null ,null ,null ,null ,null ,null,"P1",null ,null,null ,null,null ,null ,null },
				      {"I18",null,null,null,null ,null ,null ,null ,null ,null ,null ,null ,null,null,null ,null,null ,null,null ,null ,null },
				      {"I7" ,"I4","I5","I6",null ,null ,null ,null ,null ,null ,null ,null ,null,null,"I19","I2",null ,"I3",null ,null ,null },
				      {null ,null,null,null,null ,"I20","I21","I22",null ,null ,null ,null ,null,null,null ,null,null ,null,null ,null ,null },
				      {null ,null,null,null,null ,"P11","P11","P11","I23","I24",null ,"P11",null,null,null ,null,null ,null,null ,null ,null },
				      {null ,null,null,null,null ,"P14","P14","P14","P14","P14",null ,"P14",null,null,null ,null,null ,null,null ,null ,null },
				      {"I17",null,null,null,null ,null ,null ,null ,null ,null ,"I16",null ,null,null,null ,null,null ,null,"I25","I14","I15"},
				      {null ,null,null,null,null ,"P16","P16","P16","P16","P16",null ,"P16",null,null,null ,null,null ,null,null ,null ,null },
				      {null ,null,null,null,"I11","I12",null ,null ,null ,null ,null ,null ,null,null,null ,null,"I26",null,null ,null ,null },
				      {null ,null,null,null,null ,null ,null ,null ,null ,null ,null ,null ,null,"P7",null ,null,null ,null,null ,null ,null },
				      {null ,null,null,null,null ,null ,null ,null ,null ,null ,null ,null ,null,"P8",null ,null,null ,null,null ,null ,null },
				      {"I17",null,null,null,null ,null ,null ,null ,null ,null ,"I16",null ,null,null,null ,null,null ,null,null ,"I27","I15"},
				      {"I17",null,null,null,null ,null ,null ,null ,null ,null ,"I16",null ,null,null,null ,null,null ,null,null ,"I28","I15"},
				      {"I17",null,null,null,null ,null ,null ,null ,null ,null ,"I16",null ,null,null,null ,null,null ,null,null ,null ,"I29"},
				      {"I17",null,null,null,null ,null ,null ,null ,null ,null ,"I16",null ,null,null,null ,null,null ,null,null ,null ,"I30"},
				      {null ,null,null,null,null ,null ,"I21","I22",null ,null ,null ,"I31",null,null,null ,null,null ,null,null ,null ,null },
				      {null ,null,null,null,null ,null ,null ,null ,null ,null ,null ,null ,null,"P6",null ,null,null ,null,null ,null ,null },
				      {null ,null,null,null,null ,"P9" ,"P9" ,"P9" ,"I23","I24",null ,"P9" ,null,null,null ,null,null ,null,null ,null ,null },
				      {null ,null,null,null,null ,"P10","P10","P10","I23","I24",null ,"P10",null,null,null ,null,null ,null,null ,null ,null },
				      {null ,null,null,null,null ,"P12","P12","P12","P12","P12",null ,"P12",null,null,null ,null,null ,null,null ,null ,null },
				      {null ,null,null,null,null ,"P13","P13","P13","P13","P13",null ,"P13",null,null,null ,null,null ,null,null ,null ,null },
				      {null ,null,null,null,null ,"P15","P15","P15","P15","P15",null ,"P15",null,null,null ,null,null ,null,null ,null ,null },};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaz frame = new Interfaz();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Interfaz() {
		setResizable(false);
		setTitle("Programa LR");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 1164, 539);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton analizar = new JButton("Analizar");
		analizar.setFont(new Font("Tahoma", Font.PLAIN, 48));
		analizar.setBackground(new Color(204, 255, 204));
		analizar.setBounds(798, 325, 356, 203);
		panel.add(analizar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 300, 517);
		panel.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(320, 11, 834, 303);
		panel.add(scrollPane_1);
		modelo = new DefaultTableModel();
		modelo.addColumn("Entrada");
		modelo.addColumn("Pila");
		modelo.addColumn("Acción");
		modelo.addColumn("Pila Semantica");
		
		table = new JTable();
		table.setModel(modelo);
		table.getColumnModel().getColumn(0).setPreferredWidth(130);
		table.getColumnModel().getColumn(1).setPreferredWidth(180);
		table.getColumnModel().getColumn(2).setPreferredWidth(120);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		scrollPane_1.setViewportView(table);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(320, 325, 468, 203);
		panel.add(scrollPane_2);
		
		modeloLexico = new DefaultTableModel();
		modeloLexico.addColumn("Componente");
		modeloLexico.addColumn("Tipo");
		modeloLexico.addColumn("Tipo Dato");
		
		table_1 = new JTable();
		table_1.setModel(modeloLexico);
		scrollPane_2.setViewportView(table_1);
		
		setFilas(nfilas);
		LR = new AnalisisLR(filas, columnas, mat, NTProducciones, producciones, this);
		
		analizar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				modelo.setRowCount(0);
				modeloLexico.setRowCount(0);
				LR.establecerCadena(textArea.getText());
			}
		});
	}
	public void setFilas(int nfilas)
	{
		filas = new String[nfilas];
		
		for(int i=0;i<nfilas;i++)
			filas[i] = "I" + i;
	}
}
