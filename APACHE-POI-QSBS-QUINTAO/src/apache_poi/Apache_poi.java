package apache_poi;

import java.awt.BorderLayout;



import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.ButtonGroup;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class Apache_poi extends JFrame {

	private JPanel contentPane;
	private JTextField txtFile;
	private String arquivo = "";
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Apache_poi frame = new Apache_poi();
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
	public Apache_poi() {
		setTitle("Ordena\u00E7\u00E3o de arquivos de Excel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 479, 203);
		contentPane = new JPanel();
		contentPane.setToolTipText("ANDANDO");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Escolha o arquivo:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(10, 28, 113, 14);
		contentPane.add(lblNewLabel);

		JRadioButton rbBubble = new JRadioButton("BubbleSort");
		rbBubble.setEnabled(false);
		rbBubble.setSelected(true);
		buttonGroup.add(rbBubble);
		rbBubble.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rbBubble.setBounds(6, 125, 85, 23);
		contentPane.add(rbBubble);

		JRadioButton rbQuick = new JRadioButton("QuickSort");
		rbQuick.setEnabled(false);
		buttonGroup.add(rbQuick);
		rbQuick.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rbQuick.setBounds(115, 125, 85, 23);
		contentPane.add(rbQuick);

		JLabel lblNewLabel_1 = new JLabel("Selecione o m\u00E9todo:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(10, 97, 129, 14);
		contentPane.add(lblNewLabel_1);
		
		JButton btnExecutar = new JButton("EXECUTAR");
		btnExecutar.setEnabled(false);

		JButton btnAbrir = new JButton("ABRIR");
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser(); 
				fileChooser.setDialogTitle("Selecione o arquivo do MS-Excel");
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

				FileNameExtensionFilter filter = new FileNameExtensionFilter("planilhas", "xls", "xlsx");

				fileChooser.setFileFilter(filter);
				int retorno = fileChooser.showOpenDialog(null);

				if (retorno == JFileChooser.APPROVE_OPTION) {

					File file = fileChooser.getSelectedFile();

					textField.setText(file.getPath());

					arquivo = file.getPath();

				}

				rbBubble.setEnabled(true);
				rbQuick.setEnabled(true);
				rbBubble.isSelected();
				btnExecutar.setEnabled(true);


			}
		});
		btnAbrir.setMnemonic('a');
		btnAbrir.setBounds(358, 52, 89, 23);
		contentPane.add(btnAbrir);

		JButton btnSair = new JButton("SAIR");
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnSair.setBounds(358, 125, 89, 24);
		contentPane.add(btnSair);

		
		btnExecutar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				FileInputStream fisPlanilha = null;
				try {
					fisPlanilha = new FileInputStream(arquivo);

					//cria um workbook = planilha toda com todas as abas
					XSSFWorkbook workbook = new XSSFWorkbook(fisPlanilha);

					//recuperamos apenas a primeira aba ou primeira planilha
					XSSFSheet sheet = workbook.getSheetAt(0);

					//recebe a quantidade de linhas da planilha
					int linhas = sheet.getLastRowNum();

					//cria o vetor que receberá os dados da planilha de acordo com o total de linhas
					int[] planilha = new int[linhas+1]; 

					//instancia a classe ROW
					Row row ;

					//loop que varre toda a extensao da planilha e insere os valores no vetor
					for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) 
					{
						row = sheet.getRow(i);
						Cell cell = row.getCell(0);
						planilha[i] = (int) cell.getNumericCellValue();

					}

					//decisão sobre o metodo de organização...
					if (rbBubble.isSelected()) {

						//instancia a BubbleSort...
						Bubble bubble = new Bubble(); 

						//objeto criado executa no vetor o método BubbleSort... 
						bubble.Bubble(planilha);
					}							

					if (rbQuick.isSelected()) {

						//instancia a classeQuickSort...
						Quick quick = new Quick();  //comentei devido a estar ativa a bubbleSort

						//objeto criado executa no vetor o método QuickSort... 
						quick.Quick(planilha, 0, planilha.length-1);

					}    


					//loop que preenche o vetor com o metodo já executado
					for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) 
					{
						row = sheet.getRow(i);
						Cell cellDestino = row.getCell(1);
						if(cellDestino == null) 
						{
							cellDestino = sheet.getRow(i).createCell(1);
							cellDestino.setCellValue(planilha[i]);
						}
					}

					//preenche (escreve) na planilha os valores no vetor
					FileOutputStream outFile = new FileOutputStream(new File(arquivo));
					workbook.write(outFile);

					//encerra a leitura e a escrita do arquivo..
					outFile.close();
					fisPlanilha.close();    

					JOptionPane.showMessageDialog(null, "Método de ordenação exceutado com sucesso!");


				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnExecutar.setMnemonic('e');
		btnExecutar.setBounds(219, 125, 119, 24);
		contentPane.add(btnExecutar);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(10, 53, 328, 20);
		contentPane.add(textField);

	}
}
