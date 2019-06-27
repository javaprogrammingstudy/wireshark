package Model;

import java.util.ArrayList;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;

public class Device {
	
	public static void main(String[] args) {
		Pcap pcap = getPcap();
	}

	public static Pcap getPcap() {
		ArrayList<PcapIf> allDevs = new ArrayList<PcapIf>(); //����̽��� ���� ������ arraylist�� ����
		StringBuilder errbuf = new StringBuilder(); //���� ó��
		
		int r = Pcap.findAllDevs(allDevs, errbuf); //���� ������ ����̽��� allDevs�� ����. 2��° ���ڴ� ����ó��
		
		if((r==Pcap.NOT_OK) || allDevs.isEmpty()) {
			System.out.println("��Ʈ��ũ ��ġ ã�� ����" + errbuf.toString());
			return null;
		} //����ó��
		
		System.out.println("< Ž���� ��Ʈ��ũ Device >");
		int i=0;
		
		for(PcapIf device : allDevs) { //Ž���� ��� ���
			String description = (device.getDescription() != null ) ? device.getDescription() : "��� ���� ������ �����ϴ�.";
			System.out.printf("[%d��]: %s [%s]\n", ++i, device.getName(), description);
		}
		
		PcapIf device = allDevs.get(1);
		System.out.printf("���õ� ��ġ: %s\n", (device.getDescription() != null) ? device.getDescription() : device.getName());
		
		int snaplen = 64*1024; //65536Byte��ŭ ��Ŷ ĸ��
		int flags = Pcap.MODE_NON_PROMISCUOUS; //���������
		int timeout = 10*1000;	//Ÿ�Ӿƿ� 10��
		Pcap pcap = Pcap.openLive(device.getName(), snaplen, flags, timeout, errbuf);
		
		if (pcap == null) {
			System.out.printf("Network Device Access Failed. Error: "+ errbuf.toString());
			return null;
		}
		
		return pcap;
	}	
}