package View;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.SwingConstants;

public class MainView extends JFrame implements ActionListener, Runnable{

	public JTextField text_filter;
	public JPanel pan_fst, pan_sec, pan_thd, pan_four, pan_fiv, pan_six;
	public JButton  btn_run= new JButton("RUN");
	public JButton btn_stop= new JButton("STOP"); 
	public JLabel la_filter;
	public JMenuBar menubar;
	public JMenu file, edit, view, go;	
	
	public JTextArea area_packinfo;
	public JScrollPane scrol_packinfo;
	/**
	 * Create the frame.
	 */
	
	Scanner in;  
	
	public MainView() {
		
		// JFrame
		setResizable(false); // frame 크기 조절
		setTitle("WireShark"); // frame title
		//getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER,0,30));
		getContentPane().setLayout(new BorderLayout(10,30));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료 버튼
		setBounds(300, 100, 800, 600);   
		// JPanel
		// 1.메뉴바 
		menubar = new JMenuBar();
		file = new JMenu("file");
		edit = new JMenu("edit");
		view = new JMenu("view");
		go = new JMenu("go");
		//pan_fst.setLayout(new GridLayout(1,4));
		
		menubar.add(file);
		menubar.add(edit);
		menubar.add(view);
		menubar.add(go);
		
		setJMenuBar(menubar);
		
		// 2. 실행 및 정지 버튼 담을 판넬
		//setContentPane(pan_sec);
		pan_sec = new JPanel();
		btn_run= new JButton("RUN");
		btn_stop= new JButton("STOP");
		pan_sec.add(btn_run);
		pan_sec.add(btn_stop);
		

	
		
		// 3. 필터 담을 판넬(보류)
		pan_thd = new JPanel();
		text_filter = new JTextField(10);
		la_filter = new JLabel("filter : ");
		pan_thd.add(la_filter);
		pan_thd.add(text_filter);
		//setContentPane(pan_thd);
		//pan_thd.setVisible(true);
		
		
		// 4. 캡처된 패킷의 자세한 정보 요약하는 판넬
		
		
		pan_four = new JPanel();
		pan_four.setLayout(new GridLayout(6,1));
		area_packinfo = new JTextArea();
		scrol_packinfo = new JScrollPane(area_packinfo);
		scrol_packinfo.setBounds(300, 500, 800, 200);
		pan_four.add(new JLabel("    No.                  Time                Source                                         Destination                    Protocol                          Inof"));
		pan_four.add(scrol_packinfo);
		//pan_four.add(area_packinfo);
		//setContentPane(pan_four);
		//pan_four.setBounds(300, 500, 800, 200);
		//pan_four.set
		
		// 5. 4번에 나아가 사에한 정보 표시하는 판넬
		pan_fiv = new JPanel();
		pan_fiv.setLayout(new GridLayout(6,1));
		//setContentPane(pan_fiv);
	
		// 6. 패킷의 데이터를 헥사값으로 표시할 판넬
		pan_six = new JPanel();
		pan_six.setLayout(new GridLayout(6,1));
		//setContentPane(pan_six);
		
		//add(pan_sec);
		//add(pan_four);
		//add(pan_thd);
		//add(pan_fiv);
		//add(pan_six);
		add("North",pan_sec);
		add("Center",pan_four);
		add("South",pan_thd);
		add("West",pan_fiv);
		add("East",pan_six);

		setVisible(true);
	}
	@Override
	public void run() {
		while (true) {
			String msg = in.nextLine(); 
			area_packinfo.append(msg+"\n");
			
			// 스크롤바 따라 내려오게 하려고
			area_packinfo.setCaretPosition(area_packinfo.getText().length());
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		new MainView();
	}
}
