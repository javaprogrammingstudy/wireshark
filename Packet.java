package Model;

import java.util.Date;

import org.jnetpcap.Pcap;
import org.jnetpcap.PcapHeader;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.nio.JMemory;
import org.jnetpcap.packet.JRegistry;
import org.jnetpcap.packet.Payload;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.packet.format.FormatUtils;
import org.jnetpcap.protocol.lan.Ethernet;
import org.jnetpcap.protocol.network.Icmp;
import org.jnetpcap.protocol.network.Ip4;
import org.jnetpcap.protocol.tcpip.Tcp;
import org.jnetpcap.protocol.tcpip.Udp;

public class Packet {
	
	public static void main(String[] args) {
		PcapPacketHandler<String> jPacketHandler = new PcapPacketHandler<String>() {
			@Override
				public void nextPacket(PcapPacket packet, String user) {
					Date captureTime = new Date(packet.getCaptureHeader().timestampInMillis());
					int dataLength = packet.getCaptureHeader().caplen();
					System.out.printf("capture time: %s\ncapture length: %d\n", captureTime, dataLength );
			}
		};
		
		//������ ��ü ����
		Icmp icmp = new Icmp();
		Ethernet eth = new Ethernet();
		Ip4 ip = new Ip4();
		Tcp tcp = new Tcp();
		Udp udp = new Udp();
		Payload payload = new Payload();
		PcapHeader header = new PcapHeader(JMemory.POINTER);
		JBuffer buf = new JBuffer(JMemory.POINTER);
		
		Pcap pcap=Device.getPcap();
		int id = JRegistry.mapDLTToId(pcap.datalink()); //pcap�� datalink ������ jNetPcap�� �������� id�� ����
		
		while(pcap.nextEx(header, buf) == Pcap.NEXT_EX_OK) { //����� ���۸� �Ǿ
			PcapPacket packet = new PcapPacket(header, buf);
			
			packet.scan(id); //���ο� ��Ŷ�� ��ĵ�Ͽ� ���Ե� ����� ã�´�
			System.out.printf("[ #%d ]\n", packet.getFrameNumber());
			System.out.println("#############packet#############");
			if(packet.hasHeader(icmp)) {
				System.out.println("icmp");
				//System.out.printf("����� MAC �ּ� = %s\n������ MAC �ּ� = %s\n" ,macSource(packet, eth), macDestination(packet, eth));
			}
			if (packet.hasHeader(eth)) {
				System.out.printf("����� MAC �ּ� = %s\n������ MAC �ּ� = %s\n" ,macSource(packet, eth), macDestination(packet, eth));
			}
			if (packet.hasHeader(ip)) {
				System.out.printf("����� IP �ּ� = %s\n������ IP �ּ� = %s\n" ,ipSource(packet, ip) , ipDestination(packet, ip));
			}
			if (packet.hasHeader(tcp)) {
				System.out.printf("����� TCP ��Ʈ = %d\n������ TCP ��Ʈ = %d\n" , tcpSource(packet, tcp), tcpDestination(packet, tcp));
			}
			if (packet.hasHeader(udp)) {
				System.out.printf("����� UDP ��Ʈ = %d\n������ UDP ��Ʈ = %d\n" , udpSource(packet, udp), udpDestination(packet, udp));
			}
			if (packet.hasHeader(payload)) {
				System.out.printf("���̷ε��� ���� = %d\n", getlength(packet, payload));
				System.out.print(hexdump(packet, payload)); //hexdump ���
			}
			pcap.loop(1, jPacketHandler, "jNetPcap");
			
		}
			
		pcap.close();
	
	}
	/*	icmp �ּҰ� ip�ּҶ� ��..? ��ã��
	public static String icmpSource(PcapPacket packet, Icmp icmp) {
		
	}
	*/
	public static String macSource(PcapPacket packet, Ethernet eth) {
			String source = FormatUtils.mac(eth.source());
			return source;
		}
	public static String macDestination(PcapPacket packet, Ethernet eth) {
		String destination = FormatUtils.mac(eth.destination());
		return destination;
	}
	
	public static String ipSource(PcapPacket packet, Ip4 ip) {
		String source = FormatUtils.ip(ip.source());
		return source;	
		}
	public static String ipDestination(PcapPacket packet, Ip4 ip) {
		String destination = FormatUtils.ip(ip.destination());
		return destination;
	}
	public static int tcpSource(PcapPacket packet, Tcp tcp) {
		return tcp.source();
	}
	
	public static int tcpDestination(PcapPacket packet, Tcp tcp) {
		return tcp.destination();
	}
	public static int udpSource(PcapPacket packet, Udp udp) {
		return udp.source();
	}
	
	public static int udpDestination(PcapPacket packet, Udp udp) {
		return udp.destination();
	}
	
	public static int getlength(PcapPacket packet, Payload payload) {
		return payload.getLength();
	}
	
	public static String hexdump(PcapPacket packet, Payload payload) {
		return payload.toHexdump();
	}
	
	}
	


