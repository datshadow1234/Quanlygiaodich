package Business.Control.OpenFormEditGD;
import java.util.ArrayList;
import java.util.List;


import Persistence.OpenFormEditGD.*;
public class OpenEditGiaoDichFormUC {

    private OpenEditGiaoDichFormGateway gateway;

    public OpenEditGiaoDichFormUC(OpenEditGiaoDichFormGateway gateway) {
        this.gateway = gateway;
    }


    // Lấy danh sách ngành học (Major) từ database → đổi sang ResMajorDTO → trả về.
    public List<ResTypeGDDTO> execute() {
        List<TypeGDDTO> listDTO = null;
        try {
            listDTO = this.gateway.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception
        }
        return convertToResTypeGDDTO(listDTO);
    }

    // chuyển đổi từ MajorDTO sang ResMajorDTO
    private List<ResTypeGDDTO> convertToResTypeGDDTO(List<TypeGDDTO> typeGDDTO) {
       List<ResTypeGDDTO> list = new ArrayList<ResTypeGDDTO>();
       for (TypeGDDTO type : typeGDDTO) {
           ResTypeGDDTO resTypeGD = new ResTypeGDDTO();

           resTypeGD.name = type.name;
           list.add(resTypeGD);
       }
        
        return list;
        
    }

}
