/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE GCS_DESTINATION_CONFIG_REPORT PACKING
package com.MAVLink.ardupilotmega;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;
        
/**
* GCS Destination Config Status
*/
public class msg_gcs_destination_config_report extends MAVLinkMessage{

    public static final int MAVLINK_MSG_ID_GCS_DESTINATION_CONFIG_REPORT = 12015;
    public static final int MAVLINK_MSG_ID_GCS_DESTINATION_CONFIG_REPORT_CRC = 196;
    public static final int MAVLINK_MSG_LENGTH = 16;
    private static final long serialVersionUID = MAVLINK_MSG_ID_GCS_DESTINATION_CONFIG_REPORT;


      
    /**
    * LTE Link GCS Destination IP Address[7:0]
    */
    public short lte_gcs_dest_ip_addr0;
      
    /**
    * LTE Link GCS Destination IP Address[15:8]
    */
    public short lte_gcs_dest_ip_addr1;
      
    /**
    * LTE Link GCS Destination IP Address[23:16]
    */
    public short lte_gcs_dest_ip_addr2;
      
    /**
    * LTE Link GCS Destination IP Address[31:24]
    */
    public short lte_gcs_dest_ip_addr3;
      
    /**
    * Private Link GCS Destination IP Address[7:0]
    */
    public short pl_gcs_dest_ip_addr0;
      
    /**
    * Private Link GCS Destination IP Address[15:8]
    */
    public short pl_gcs_dest_ip_addr1;
      
    /**
    * Private Link GCS Destination IP Address[23:16]
    */
    public short pl_gcs_dest_ip_addr2;
      
    /**
    * Private Link GCS Destination IP Address[31:24]
    */
    public short pl_gcs_dest_ip_addr3;
      
    /**
    * LTE Link Video Stream UDP Port Number[7:0]
    */
    public short lte_video_port_num_lsb;
      
    /**
    * LTE Link Video Stream UDP Port Number[15:8]
    */
    public short lte_video_port_num_msb;
      
    /**
    * Private Link Video Stream UDP Port Number[7:0]
    */
    public short pl_video_port_num_lsb;
      
    /**
    * Private Link Video Stream UDP Port Number[15:8]
    */
    public short pl_video_port_num_msb;
      
    /**
    * LTE Link Message Data TCP Port Number[7:0]
    */
    public short lte_msg_data_port_num_lsb;
      
    /**
    * LTE Link Message Data TCP Port Number[15:8]
    */
    public short lte_msg_data_port_num_msb;
      
    /**
    * Private Link Message Data TCP Port Number[7:0]
    */
    public short pl_msg_data_port_num_lsb;
      
    /**
    * Private Link Message Data TCP Port Number[15:8]
    */
    public short pl_msg_data_port_num_msb;
    

    /**
    * Generates the payload for a mavlink message for a message of this type
    * @return
    */
    public MAVLinkPacket pack(){
        MAVLinkPacket packet = new MAVLinkPacket(MAVLINK_MSG_LENGTH);
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_GCS_DESTINATION_CONFIG_REPORT;
        packet.crc_extra = MAVLINK_MSG_ID_GCS_DESTINATION_CONFIG_REPORT_CRC;
              
        packet.payload.putUnsignedByte(lte_gcs_dest_ip_addr0);
              
        packet.payload.putUnsignedByte(lte_gcs_dest_ip_addr1);
              
        packet.payload.putUnsignedByte(lte_gcs_dest_ip_addr2);
              
        packet.payload.putUnsignedByte(lte_gcs_dest_ip_addr3);
              
        packet.payload.putUnsignedByte(pl_gcs_dest_ip_addr0);
              
        packet.payload.putUnsignedByte(pl_gcs_dest_ip_addr1);
              
        packet.payload.putUnsignedByte(pl_gcs_dest_ip_addr2);
              
        packet.payload.putUnsignedByte(pl_gcs_dest_ip_addr3);
              
        packet.payload.putUnsignedByte(lte_video_port_num_lsb);
              
        packet.payload.putUnsignedByte(lte_video_port_num_msb);
              
        packet.payload.putUnsignedByte(pl_video_port_num_lsb);
              
        packet.payload.putUnsignedByte(pl_video_port_num_msb);
              
        packet.payload.putUnsignedByte(lte_msg_data_port_num_lsb);
              
        packet.payload.putUnsignedByte(lte_msg_data_port_num_msb);
              
        packet.payload.putUnsignedByte(pl_msg_data_port_num_lsb);
              
        packet.payload.putUnsignedByte(pl_msg_data_port_num_msb);
        
        return packet;
    }

    /**
    * Decode a gcs_destination_config_report message into this class fields
    *
    * @param payload The message to decode
    */
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
              
        this.lte_gcs_dest_ip_addr0 = payload.getUnsignedByte();
              
        this.lte_gcs_dest_ip_addr1 = payload.getUnsignedByte();
              
        this.lte_gcs_dest_ip_addr2 = payload.getUnsignedByte();
              
        this.lte_gcs_dest_ip_addr3 = payload.getUnsignedByte();
              
        this.pl_gcs_dest_ip_addr0 = payload.getUnsignedByte();
              
        this.pl_gcs_dest_ip_addr1 = payload.getUnsignedByte();
              
        this.pl_gcs_dest_ip_addr2 = payload.getUnsignedByte();
              
        this.pl_gcs_dest_ip_addr3 = payload.getUnsignedByte();
              
        this.lte_video_port_num_lsb = payload.getUnsignedByte();
              
        this.lte_video_port_num_msb = payload.getUnsignedByte();
              
        this.pl_video_port_num_lsb = payload.getUnsignedByte();
              
        this.pl_video_port_num_msb = payload.getUnsignedByte();
              
        this.lte_msg_data_port_num_lsb = payload.getUnsignedByte();
              
        this.lte_msg_data_port_num_msb = payload.getUnsignedByte();
              
        this.pl_msg_data_port_num_lsb = payload.getUnsignedByte();
              
        this.pl_msg_data_port_num_msb = payload.getUnsignedByte();
        
    }

    /**
    * Constructor for a new message, just initializes the msgid
    */
    public msg_gcs_destination_config_report(){
        msgid = MAVLINK_MSG_ID_GCS_DESTINATION_CONFIG_REPORT;
    }

    /**
    * Constructor for a new message, initializes the message with the payload
    * from a mavlink packet
    *
    */
    public msg_gcs_destination_config_report(MAVLinkPacket mavLinkPacket){
        this.sysid = mavLinkPacket.sysid;
        this.compid = mavLinkPacket.compid;
        this.msgid = MAVLINK_MSG_ID_GCS_DESTINATION_CONFIG_REPORT;
        unpack(mavLinkPacket.payload);
    }

                                    
    /**
    * Returns a string with the MSG name and data
    */
    public String toString(){
        return "MAVLINK_MSG_ID_GCS_DESTINATION_CONFIG_REPORT - sysid:"+sysid+" compid:"+compid+" lte_gcs_dest_ip_addr0:"+lte_gcs_dest_ip_addr0+" lte_gcs_dest_ip_addr1:"+lte_gcs_dest_ip_addr1+" lte_gcs_dest_ip_addr2:"+lte_gcs_dest_ip_addr2+" lte_gcs_dest_ip_addr3:"+lte_gcs_dest_ip_addr3+" pl_gcs_dest_ip_addr0:"+pl_gcs_dest_ip_addr0+" pl_gcs_dest_ip_addr1:"+pl_gcs_dest_ip_addr1+" pl_gcs_dest_ip_addr2:"+pl_gcs_dest_ip_addr2+" pl_gcs_dest_ip_addr3:"+pl_gcs_dest_ip_addr3+" lte_video_port_num_lsb:"+lte_video_port_num_lsb+" lte_video_port_num_msb:"+lte_video_port_num_msb+" pl_video_port_num_lsb:"+pl_video_port_num_lsb+" pl_video_port_num_msb:"+pl_video_port_num_msb+" lte_msg_data_port_num_lsb:"+lte_msg_data_port_num_lsb+" lte_msg_data_port_num_msb:"+lte_msg_data_port_num_msb+" pl_msg_data_port_num_lsb:"+pl_msg_data_port_num_lsb+" pl_msg_data_port_num_msb:"+pl_msg_data_port_num_msb+"";
    }
}
        