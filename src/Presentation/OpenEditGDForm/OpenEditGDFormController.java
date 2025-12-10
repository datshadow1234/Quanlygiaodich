package Presentation.OpenEditGDForm;
import java.util.ArrayList;
import java.util.List;

import Business.Control.OpenFormEditGD.OpenEditGiaoDichFormUC;
import Business.Control.OpenFormEditGD.ResTypeGDDTO;
public class OpenEditGDFormController {
    private OpenEditGDFormModel model;
    private OpenEditGiaoDichFormUC useCase;

    public OpenEditGDFormController(OpenEditGDFormModel model, OpenEditGiaoDichFormUC useCase) {
        this.model = model;
        this.useCase = useCase;
    }

    public void execute() {
       List<ResTypeGDDTO> majorDTOs =  useCase.execute();
        model.listItem = convertToGDTypeitem(majorDTOs);
        model.notifySubscriber();
    }


    // chuyển đổi danh sách ResMajorDTO thành danh sách MajorItem
    private List<GiaoDichTypeItem> convertToGDTypeitem(List<ResTypeGDDTO>  gDTypeDTOs){
		List<GiaoDichTypeItem> list = new ArrayList<GiaoDichTypeItem>();

		for (ResTypeGDDTO resMajorDTO : gDTypeDTOs) {
			GiaoDichTypeItem item = new GiaoDichTypeItem();
			item.name = resMajorDTO.name;
			list.add(item);
		}
		
		return list;
	}
	
}
