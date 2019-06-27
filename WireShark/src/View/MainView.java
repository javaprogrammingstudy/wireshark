package View;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainView extends JFrame implements ActionListener, Runnable {

	public JTextField text_filter;
	public JPanel upper_pan, lower_pan, Main_pan, pan_fst, pan_btn, pan_filter, pan_packetTable, pan_PacketInfo, pan_PacketHexCode;
	public JButton btn_run = new JButton("RUN");
	public JButton btn_stop = new JButton("STOP");
	public JLabel la_filter;
	public JMenuBar menubar;
	public JMenu file, edit, view, go;
	public JTable table;

	public JTextArea area_packinfo;
	public JScrollPane scrol_packinfo;
	/**
	 * Create the frame.
	 */

	// packet_table을 텍스트 필드에서 JTable로 바꿀까하는 실험하는 중
	String title[] = { "No.", "Time", "Source", "Destination", "Protocol", "Length", "Internet Protocol" };
	String data[][];
	//
	
	Scanner in;

	public MainView() {

		// JFrame
		Main_pan = new JPanel(new GridLayout(3, 1));
		// 하나의 Main 패널 안에 upper_pan, packet_table, lower_pan이 담김
		upper_pan = new JPanel(new GridLayout(3, 1));
		lower_pan = new JPanel(new GridLayout(2, 1));

		setResizable(false); // frame 크기 조절
		setTitle("WireShark"); // frame title
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료 버튼
		setBounds(300, 100, 800, 600); // wireshark 사이즈

		// 1.메뉴바
		menubar = new JMenuBar();

		// file 메뉴와 아이템 객체들
		file = new JMenu("file");
		JMenuItem open = new JMenuItem("open");
		JMenuItem close = new JMenuItem("close");
		JMenuItem save = new JMenuItem("save");
		file.add(open);
		file.add(close);
		file.add(save);

		// edit 메뉴와 아이템 객체들
		edit = new JMenu("edit");
		JMenuItem FindPacket = new JMenuItem("FindPacket");
		JMenuItem MarkUnMark = new JMenuItem("Mark / UnMark");
		JMenuItem Ignore = new JMenuItem("Ignore");
		edit.add(FindPacket);
		edit.add(MarkUnMark);
		edit.add(Ignore);

		// view 메뉴와 아이템 객체들
		view = new JMenu("view");
		JMenuItem ShowPacketinNewWindow = new JMenuItem("Show Packet in New Window");
		view.add(ShowPacketinNewWindow);

		// go 메뉴와 아이템 객체들
		go = new JMenu("go");
		JMenuItem Gotopacket = new JMenuItem("Go to packet");
		JMenuItem Previouspacket = new JMenuItem("Previous packet");
		JMenuItem Nextpacket = new JMenuItem("Next packet");
		JMenuItem Firstpacket = new JMenuItem("First packet");
		JMenuItem Lastpacket = new JMenuItem("Last packet");
		go.add(Gotopacket);
		go.add(Previouspacket);
		go.add(Nextpacket);
		go.add(Firstpacket);
		go.add(Lastpacket);

		// 메뉴바에 위의 메뉴들을 전부 붙임.
		menubar.add(file);
		menubar.add(edit);
		menubar.add(view);
		menubar.add(go);

		// Frame에 메뉴바 추가
		setJMenuBar(menubar);

		
		// 2. upper_pan에 추가할 3가지 판넬
			// pan_btn : 실행 및 정지 버튼 담을 판넬
			// pan_filter : 검색을 위한 filter 판넬
			// pan_packetInfo : Packet의 정보를 나열하는 헤더가 담긴 판넬 -> 따로 객체 안만듦, add(new ~)로 바로 생성함
		pan_btn = new JPanel();
		// pan_sec.setBackground(Color.blue);
		btn_run = new JButton("RUN");
		btn_stop = new JButton("STOP");
		pan_btn.add(btn_run);
		pan_btn.add(btn_stop);

		pan_filter = new JPanel();
		// pan_thd.setBackground(Color.red);
		text_filter = new JTextField(20);
		la_filter = new JLabel("  filter : ");
		pan_filter.add(la_filter);
		pan_filter.add(text_filter);

		// 3. 캡처된 패킷의 자세한 정보 요약하는 판넬
		pan_packetTable = new JPanel();
		// pan_packetTable.setBackground(Color.yellow);
		pan_packetTable.setLayout(new GridLayout(1, 1));
		area_packinfo = new JTextArea(); 
		scrol_packinfo = new JScrollPane(area_packinfo);
		pan_packetTable.add(scrol_packinfo);
		
		// 실험중인 테이블화
		table = new JTable(data, title);

		// 4.lower 테이블
			// pan_PacketInfo : 해당 패킷에 대한 구체적인 정보 출력 
			// pan_PacketHexCode : 해당 패킷에 대한 헥사 값 출력 
		pan_PacketInfo = new JPanel();
		area_packinfo = new JTextArea();
		scrol_packinfo = new JScrollPane(area_packinfo);
		scrol_packinfo.setSize(100, 100);
		pan_PacketInfo.add(scrol_packinfo);
		pan_PacketInfo.setLayout(new GridLayout(1, 1));
		// setContentPane(pan_fiv);

		pan_PacketHexCode = new JPanel();
		area_packinfo = new JTextArea();
		scrol_packinfo = new JScrollPane(area_packinfo);
		scrol_packinfo.setSize(100, 100);
		pan_PacketHexCode.add(scrol_packinfo);
		pan_PacketHexCode.setLayout(new GridLayout(1, 1));

		// upper 팬
		upper_pan.add(pan_btn);
		upper_pan.add(pan_filter);
		upper_pan.add(new JLabel(
				"    No.                  Time                Source                                         Destination                    Protocol                          Inof"));

		// lower 팬
		lower_pan.add(pan_PacketInfo);
		lower_pan.add(pan_PacketHexCode);

		// 메인 팬 = uppper 팬 + 패킷 테이블 + lower 팬
		Main_pan.add(upper_pan);
		Main_pan.add(pan_packetTable);
		Main_pan.add(lower_pan);

		add(Main_pan);
		setVisible(true);
	}

	@Override
	public void run() {
		while (true) {
			String msg = in.nextLine();
			area_packinfo.append(msg + "\n");

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
