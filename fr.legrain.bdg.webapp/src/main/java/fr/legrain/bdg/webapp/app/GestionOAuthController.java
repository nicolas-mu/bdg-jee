package fr.legrain.bdg.webapp.app;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import fr.legrain.article.dto.TaFamilleDTO;
import fr.legrain.article.dto.TaTTvaDTO;
import fr.legrain.article.dto.TaTvaDTO;
import fr.legrain.article.dto.TaUniteDTO;
import fr.legrain.article.model.TaMarqueArticle;
import fr.legrain.bdg.dossier.service.remote.ITaInfoEntrepriseServiceRemote;
import fr.legrain.bdg.droits.service.remote.ITaUtilisateurServiceRemote;
import fr.legrain.bdg.lib.osgi.Const;
import fr.legrain.bdg.model.mapping.LgrDozerMapper;
import fr.legrain.bdg.webapp.oauth.google.GoogleBean;
import fr.legrain.bdg.webapp.oauth.microsoft.MSBean;
import fr.legrain.bdg.webapp.oauth.stripe.StripeBean;
import fr.legrain.document.dto.IDocumentPayableCB;
import fr.legrain.document.dto.TaTPaiementDTO;
import fr.legrain.document.model.TaTPaiement;
import fr.legrain.droits.service.SessionInfo;
import fr.legrain.email.dto.TaMessageEmailDTO;
import fr.legrain.email.dto.TaPieceJointeEmailDTO;
import fr.legrain.email.model.TaMessageEmail;
import fr.legrain.lib.data.EnumModeObjet;
import fr.legrain.lib.data.ModeObjetEcranServeur;
import fr.legrain.paiement.model.PaiementCarteBancaire;
import fr.legrain.paiement.model.RetourPaiementCarteBancaire;
import fr.legrain.tiers.dto.TaCPaiementDTO;
import fr.legrain.tiers.dto.TaEmailDTO;
import fr.legrain.tiers.dto.TaTCiviliteDTO;
import fr.legrain.tiers.dto.TaTEntiteDTO;
import fr.legrain.tiers.model.TaCPaiement;
import fr.legrain.tiers.model.TaEmail;
import fr.legrain.tiers.model.TaFamilleTiers;
import fr.legrain.tiers.model.TaTEmail;
import fr.legrain.tiers.model.TaTTvaDoc;

@Named
@ViewScoped  
public class GestionOAuthController extends AbstractController implements Serializable {  

	@Inject @Named(value="tabListModelCenterBean")
	private TabListModelBean tabsCenter;

	@Inject @Named(value="tabViewsBean")
	private TabViewsBean tabViews;

	private String paramId;	
	
	private @Inject GoogleBean googleBean;
	private @Inject MSBean msBean;
	private @Inject StripeBean stripeBean;

	protected ModeObjetEcranServeur modeEcran = new ModeObjetEcranServeur();

	protected @EJB ITaInfoEntrepriseServiceRemote taInfoEntrepriseService;

	private @EJB ITaUtilisateurServiceRemote taUtilisateurService;

	private @Inject SessionInfo sessionInfo;

	private StreamedContent streamedPieceJointe;
	private TaPieceJointeEmailDTO selectedTaPieceJointeEmailDTO;

	private TaMessageEmailDTO[] selectedTaMessageEmailDTOs; 
	private TaMessageEmail taMessageEmail = new TaMessageEmail();
	private TaMessageEmailDTO newTaMessageEmailDTO = new TaMessageEmailDTO();
	private TaMessageEmailDTO selectedTaMessageEmailDTO = new TaMessageEmailDTO();

	private TaEmail taEmail;
	private TaTEmail taTEmail;

	private LgrDozerMapper<TaMessageEmailDTO,TaMessageEmail> mapperUIToModel  = new LgrDozerMapper<TaMessageEmailDTO,TaMessageEmail>();
	private LgrDozerMapper<TaMessageEmail,TaMessageEmailDTO> mapperModelToUI  = new LgrDozerMapper<TaMessageEmail,TaMessageEmailDTO>();
	
	private LgrDozerMapper<TaEmailDTO,TaEmail> mapperUIToModelTaEmail  = new LgrDozerMapper<TaEmailDTO,TaEmail>();
	private LgrDozerMapper<TaEmail,TaEmailDTO> mapperModelToUITaEmail  = new LgrDozerMapper<TaEmail,TaEmailDTO>();
	
	public GestionOAuthController() {  
		
	}  

	@PostConstruct
	public void postConstruct(){
		refresh();
	}

	public void refresh(){

		modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		try {
//			if(sessionMap.get(EmailParam.NOM_OBJET_EN_SESSION)!=null) {
//				param = (EmailParam) sessionMap.get(EmailParam.NOM_OBJET_EN_SESSION);
//				sessionMap.remove(EmailParam.NOM_OBJET_EN_SESSION);
//			}
//			
//			if(param!=null) {
//				mapperModelToUI.map(taMessageEmail, selectedTaMessageEmailDTO);
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void autoCompleteMapUIToDTO() {
//		if(taTEmailDTO!=null) {
//			validateUIField(Const.C_CODE_T_EMAIL,taTEmailDTO.getCodeTEmail());
//			selectedTaMessageEmailDTO.setCodeTTiers(taTEmailDTO.getCodeTEmail());
//		}else {
//			selectedTaMessageEmailDTO.setCodeTTiers(null);
//		}
//		
//		if(taEmailDTO!=null) {
//			validateUIField(Const.C_CODE_eT_ENTITE,taEmailDTO.getCodeTEntite());
//			selectedTaMessageEmailDTO.setCodeTEntite(taEmailDTO.getCodeTEntite());
//		}else {
//			selectedTaMessageEmailDTO.setCodeTEntite(null);
//		}
		
	}

	public void autoCompleteMapDTOtoUI() {
		try {
			taEmail = null;
			taTEmail = null;

//			taTEmailDTO = null;
//			taEmailDTODestinataire = null;

//			if(selectedTaMessageEmailDTO.getCodeTTiers()!=null && !selectedTaMessageEmailDTO.getCodeTTiers().equals("")) {
//				taEmail = taTTiersService.findByCode(selectedTaMessageEmailDTO.getCodeTTiers());
//				taTEmailDTO=taTTiersService.findByCodeDTO(selectedTaMessageEmailDTO.getCodeTTiers());
//			}
//			if(selectedTaMessageEmailDTO.getCodeTCivilite()!=null && !selectedTaMessageEmailDTO.getCodeTCivilite().equals("")) {
//				taTEmail = taTCivlicteService.findByCode(selectedTaMessageEmailDTO.getCodeTCivilite());
//				taTCiviliteDTO = taTCivlicteService.findByCodeDTO(selectedTaMessageEmailDTO.getCodeTCivilite());
//			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	public RetourPaiementCarteBancaire envoyerEmail(PaiementCarteBancaire cb, IDocumentPayableCB ticket) throws EJBException {
//		TaCompteServiceWebExterne compte =  taCompteServiceWebExterneService.findCompteDefautPourAction(TaTServiceWebExterne.TYPE_EMAIL);
//		Boolean utiliseServiceWebExterne = taPreferencesService.retourPreferenceBoolean(ITaPreferencesServiceRemote.GROUPE_PREFERENCES_ENVOI_EMAIL, ITaPreferencesServiceRemote.UTILISE_SERVICE_WEB_EXTERNE_POUR_EMAIL);
//		String implementation = "";
//		
//		if(utiliseServiceWebExterne) {
//			if(compte!=null) {
//				switch (compte.getTaServiceWebExterne().getCodeServiceWebExterne()) {
//				case UtilServiceWebExterne.CODE_SWE_MAILJET:
//					implementation = EmailLgr.TYPE_MAILJET_DOSSIER;
//					break;
////				case UtilServiceWebExterne.CODE_SWE_PAYPAL:
////					implementation = PaiementLgr.TYPE_PAYPAL;
////					break;
//				default:
//					break;
//				}
//			}
//		} else {
//			//utilisation d'un serveur SMTP renseigné dans les préférences du dossier
//			implementation = EmailLgr.TYPE_SMTP_DOSSIER;
//			//IEnvoiEmailViaServiceExterneDossierService service = iEnvoiEmailViaServiceExterneDossierService.select(new MyQualifierEmailLiteral(implementation)).get();
//			
////			Boolean utiliseServiceWebExterne = taPreferencesService.retourPreferenceBoolean(ITaPreferencesServiceRemote.GROUPE_PREFERENCES_ENVOI_EMAIL, ITaPreferencesServiceRemote.UTILISE_SERVICE_WEB_EXTERNE_POUR_EMAIL);
//		}
//		
//		IEnvoiEmailViaServiceExterneDossierService service = iEnvoiEmailViaServiceExterneDossierService.select(new MyQualifierEmailLiteral(implementation)).get();
//		return service.sendAndLog(compte, cb, ticket);

		return null;
	}
	
	public void actFermerDialog(ActionEvent actionEvent) {
		//PrimeFaces.current().dialog().closeDynamic(null);
		
		//PrimeFaces.current().dialog().closeDynamic(listeControle);
		PrimeFaces.current().dialog().closeDynamic(null);
	}

	public void actFermer(ActionEvent actionEvent) {

		switch (modeEcran.getMode()) {
		case C_MO_INSERTION:
			//actAnnuler(actionEvent);
			break;
		case C_MO_EDITION:
			//actAnnuler(actionEvent);							
			break;

		default:
			break;
		}
		selectedTaMessageEmailDTO=new TaMessageEmailDTO();
		selectedTaMessageEmailDTOs=new TaMessageEmailDTO[]{selectedTaMessageEmailDTO};

		
	}

	public boolean etatBouton(String bouton) {
		boolean retour = true;
		switch (modeEcran.getMode()) {
		case C_MO_INSERTION:
			switch (bouton) {
			case "annuler":
			case "enregistrer":
			case "fermer":
				retour = false;
				break;
			default:
				break;
			}
			break;
		case C_MO_EDITION:
			switch (bouton) {
			case "annuler":
			case "enregistrer":
			case "fermer":
				retour = false;
				break;
			default:
				break;
			}
			break;
		case C_MO_CONSULTATION:
			switch (bouton) {
			case "inserer":
			case "fermer":
				retour = false;
				break;
			case "supprimerListe":retour = false;break;	
			case "supprimer":
			case "modifier":
			case "imprimer":
				if(selectedTaMessageEmailDTO!=null && selectedTaMessageEmailDTO.getId()!=null  && selectedTaMessageEmailDTO.getId()!=0 ) retour = false;
				break;				
			default:
				break;
			}
			break;
		default:
			break;
		}

		return retour;

	}

	public TaMessageEmailDTO[] getSelectedTaMessageEmailDTOs() {
		return selectedTaMessageEmailDTOs;
	}

	public void setSelectedTaMessageEmailDTOs(TaMessageEmailDTO[] selectedTaMessageEmailDTOs) {
		this.selectedTaMessageEmailDTOs = selectedTaMessageEmailDTOs;
	}

	public TaMessageEmailDTO getNewTaMessageEmailDTO() {
		return newTaMessageEmailDTO;
	}

	public void setNewTaMessageEmailDTO(TaMessageEmailDTO newTaMessageEmailDTO) {
		this.newTaMessageEmailDTO = newTaMessageEmailDTO;
	}

	public TaMessageEmailDTO getSelectedTaMessageEmailDTO() {
		return selectedTaMessageEmailDTO;
	}

	public void setSelectedTaMessageEmailDTO(TaMessageEmailDTO selectedTaMessageEmailDTO) {
		this.selectedTaMessageEmailDTO = selectedTaMessageEmailDTO;
	}

	public TabListModelBean getTabsCenter() {
		return tabsCenter;
	}

	public void setTabsCenter(TabListModelBean tabsCenter) {
		this.tabsCenter = tabsCenter;
	}

	public String getParamId() {
		return paramId;
	}

	public void setParamId(String paramId) {
		this.paramId = paramId;
	} 

	public void validateTiers(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		String messageComplet = "";

		try {
			//String selectedRadio = (String) value;

			String nomChamp =  (String) component.getAttributes().get("nomChamp");

			//msg = "Mon message d'erreur pour : "+nomChamp;

			//			  validateUIField(nomChamp,value);
			//			  TaMessageEmailDTO dtoTemp=new TaMessageEmailDTO();
			//			  PropertyUtils.setSimpleProperty(dtoTemp, nomChamp, value);
			//			  taTiersService.validateDTOProperty(dtoTemp, nomChamp, ITaMessageEmailServiceRemote.validationContext );

			//				//validation automatique via la JSR bean validation
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Set<ConstraintViolation<TaMessageEmailDTO>> violations = factory.getValidator().validateValue(TaMessageEmailDTO.class,nomChamp,value);
			if (violations.size() > 0) {
				messageComplet = "Erreur de validation : ";
				//					List<IStatus> statusList = new ArrayList<IStatus>();
				for (ConstraintViolation<TaMessageEmailDTO> cv : violations) {
					//						statusList.add(ValidationStatus.error(cv.getMessage()));
					messageComplet+=" "+cv.getMessage()+"\n";
				}
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, messageComplet, messageComplet));
				//					return new MultiStatus(LibrairiesEcranPlugin.PLUGIN_ID, IStatus.ERROR,
				//							//statusList.toArray(new IStatus[statusList.size()]), "Validation errors", null);
				//							statusList.toArray(new IStatus[statusList.size()]), messageComplet, null);
			} else {
				//aucune erreur de validation "automatique", on déclanche les validations du controller
				//					if(controller!=null) {
				validateUIField(nomChamp,value);
				//						if(!s.isOK()) {
				//							return s;
				//						}
				//					}
			}
			//				return ValidationStatus.ok();

			//selectedTaMessageEmailDTO.setAdresse1Adresse("abcd");

			//			  if(selectedRadio.equals("aa")) {
			//				  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
			//			  }

		} catch(Exception e) {
			//messageComplet += e.getMessage();
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, messageComplet, messageComplet));
		}
	}

	public void autcompleteSelection(SelectEvent event) {
		Object value = event.getObject();
		//			FacesMessage msg = new FacesMessage("Selected", "Item:" + value);

		String nomChamp =  (String) event.getComponent().getAttributes().get("nomChamp");

		if(value!=null) {
			if(value instanceof String && value.equals(Const.C_AUCUN))value="";	
			if(value instanceof TaTEntiteDTO && ((TaTEntiteDTO) value).getCodeTEntite()!=null && ((TaTEntiteDTO) value).getCodeTEntite().equals(Const.C_AUCUN))value=null;	
			if(value instanceof TaTCiviliteDTO && ((TaTCiviliteDTO) value).getCodeTCivilite()!=null && ((TaTCiviliteDTO) value).getCodeTCivilite().equals(Const.C_AUCUN))value=null;
			if(value instanceof TaCPaiementDTO && ((TaCPaiementDTO) value).getCodeCPaiement()!=null && ((TaCPaiementDTO) value).getCodeCPaiement().equals(Const.C_AUCUN))value=null;
			if(value instanceof TaTPaiementDTO && ((TaTPaiementDTO) value).getCodeTPaiement()!=null && ((TaTPaiementDTO) value).getCodeTPaiement().equals(Const.C_AUCUN))value=null;
			//if(value instanceof TaTTiersDTO && ((TaTTiersDTO) value).getCodeTTiers()!=null && ((TaTTiersDTO) value).getCodeTTiers().equals(Const.C_AUCUN))value=null;
			if(value instanceof TaCPaiement && ((TaCPaiement) value).getCodeCPaiement()!=null && ((TaCPaiement) value).getCodeCPaiement().equals(Const.C_AUCUN))value=null;
			if(value instanceof TaTPaiement && ((TaTPaiement) value).getCodeTPaiement()!=null && ((TaTPaiement) value).getCodeTPaiement().equals(Const.C_AUCUN))value=null;
			if(value instanceof TaTTvaDoc && ((TaTTvaDoc) value).getCodeTTvaDoc()!=null && ((TaTTvaDoc) value).getCodeTTvaDoc().equals(Const.C_AUCUN))value=null;
			if(value instanceof TaFamilleTiers && ((TaFamilleTiers) value).getCodeFamille()!=null && ((TaFamilleTiers) value).getCodeFamille().equals(Const.C_AUCUN))value=null;
		}

		validateUIField(nomChamp,value);
	}
	
	public void autcompleteUnSelect(UnselectEvent event) {
		Object value = event.getObject();
		
		String nomChamp =  (String) event.getComponent().getAttributes().get("nomChamp");

		if(value!=null) {
			if(value instanceof String && value.equals(Const.C_AUCUN))value="";	
			if(value instanceof TaFamilleDTO && ((TaFamilleDTO) value).getCodeFamille()!=null && ((TaFamilleDTO) value).getCodeFamille().equals(Const.C_AUCUN))value=null;	
			if(value instanceof TaUniteDTO && ((TaUniteDTO) value).getCodeUnite()!=null && ((TaUniteDTO) value).getCodeUnite().equals(Const.C_AUCUN))value=null;
			if(value instanceof TaTvaDTO && ((TaTvaDTO) value).getCodeTva()!=null && ((TaTvaDTO) value).getCodeTva().equals(Const.C_AUCUN))value=null;
			if(value instanceof TaTTvaDTO && ((TaTTvaDTO) value).getCodeTTva()!=null && ((TaTTvaDTO) value).getCodeTTva().equals(Const.C_AUCUN))value=null;	
			if(value instanceof TaMarqueArticle && ((TaMarqueArticle) value).getCodeMarqueArticle()!=null && ((TaMarqueArticle) value).getCodeMarqueArticle().equals(Const.C_AUCUN))value=null;	
		}
		
//		//validateUIField(nomChamp,value);
//		try {
//			if(nomChamp.equals(Const.C_ADRESSE_EMAIL+"_DESTINATAIRE")) {
//				TaEmail entity =null;
//				if(value!=null){
//					if(value instanceof TaEmailDTO){
//	//				entity=(TaFamille) value;
//						entity = taEmailService.findByCode(((TaFamilleDTO)value).getCodeFamille());
//					}else{
//						entity = taEmailService.findByCode((String)value);
//					}
//				}else{
//					selectedTaArticleDTO.setCodeFamille("");
//				}
//				//taArticle.setTaFamille(entity);
//				for (TaEmail f : taMessageEmail.getDestinataires()) {
//					if(f.getIdEmail()==((TaEmailDTO)value).getId())
//						entity = f;
//				}
//				taMessageEmail.getDestinataires().remove(entity);
//				if(taArticle.getTaFamille()!=null && taArticle.getTaFamille().getCodeFamille().equals(entity.getCodeFamille())) {
//					taArticle.setTaFamille(null);
//					taFamilleDTO = null;
//					if(!taArticle.getTaFamilles().isEmpty()) {
//						taArticle.setTaFamille(taArticle.getTaFamilles().iterator().next());
//						//taFamilleDTO = null;
//					}
//				}
//			}
//
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
	
	}

	public boolean validateUIField(String nomChamp,Object value) {

		boolean changement=false;

		try {				
//			if(nomChamp.equals(Const.C_ADRESSE_EMAIL+"_DESTINATAIRE") 
//					|| nomChamp.equals(Const.C_ADRESSE_EMAIL+"_CC")
//					|| nomChamp.equals(Const.C_ADRESSE_EMAIL+"_BCC")) {
//				
//				TaEmail entity =null;
//				if(value!=null){
//					if(value instanceof TaEmailDTO){
////					entity=(TaFamille) value;
//						entity = taEmailService.findByCode(((TaEmailDTO)value).getAdresseEmail());
//					}else{
//						entity = taEmailService.findByCode((String)value);
//					}
//				}else{
//					//selectedTaMessageEmailDTO.setCodeFamille("");
//				}
//				TaContactMessageEmail taContactMessageEmail = new TaContactMessageEmail();
//				taContactMessageEmail.setAdresseEmail(entity.getAdresseEmail());
//				taContactMessageEmail.setTaTiers(entity.getTaTiers());
//				
//				if(nomChamp.equals(Const.C_ADRESSE_EMAIL+"_DESTINATAIRE")) {
//					taContactMessageEmail.setTaMessageEmail(taMessageEmail);
//					if(taMessageEmail.getDestinataires()==null) {
//						taMessageEmail.setDestinataires(new HashSet<>());
//					}
//					taMessageEmail.getDestinataires().add(taContactMessageEmail);
//				} else if(nomChamp.equals(Const.C_ADRESSE_EMAIL+"_CC")) {
//					taContactMessageEmail.setTaMessageEmailCc(taMessageEmail);
//					if(taMessageEmail.getCc()==null) {
//						taMessageEmail.setCc(new HashSet<>());
//					}
//					taMessageEmail.getCc().add(taContactMessageEmail);
//				} else if(nomChamp.equals(Const.C_ADRESSE_EMAIL+"_BCC")) {
//					taContactMessageEmail.setTaMessageEmailBcc(taMessageEmail);
//					if(taMessageEmail.getBcc()==null) {
//						taMessageEmail.setBcc(new HashSet<>());
//					}
//					taMessageEmail.getBcc().add(taContactMessageEmail);
//				} 
//
//			} else if(nomChamp.equals(Const.C_CODE_TIERS)) {
////		
//			}
			

			return false;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}


	public List<TaEmail> adresseEmailAutoComplete(String query) {
		return null;
//		List<TaEmail> allValues = taEmailService.selectAll();
//		List<TaEmail> filteredValues = new ArrayList<TaEmail>();
//		TaEmail civ = new TaEmail();
//		civ.setAdresseEmail(Const.C_AUCUN);
//		filteredValues.add(civ);
//		for (int i = 0; i < allValues.size(); i++) {
//			civ = allValues.get(i);
//			if(query.equals("*") || civ.getAdresseEmail().toLowerCase().contains(query.toLowerCase())) {
//				filteredValues.add(civ);
//			}
//		}
//
//		return filteredValues;
	}

	public List<TaEmailDTO> adresseEmailAutoCompleteLight(String query) {
		return null;
//		List<TaEmailDTO> allValues = taEmailService.findAllLight();
//		List<TaEmailDTO> filteredValues = new ArrayList<TaEmailDTO>();
//		TaEmailDTO civ = new TaEmailDTO();
////		civ.setAdresseEmail(Const.C_AUCUN);
////		filteredValues.add(civ);
//		for (int i = 0; i < allValues.size(); i++) {
//			civ = allValues.get(i);
//			if(query.equals("*") || civ.getAdresseEmail().toLowerCase().contains(query.toLowerCase())) {
//				filteredValues.add(civ);
//			}
//		}
//
//		return filteredValues;
	}
	

	public TabViewsBean getTabViews() {
		return tabViews;
	}

	public void setTabViews(TabViewsBean tabViews) {
		this.tabViews = tabViews;
	}

	public ModeObjetEcranServeur getModeEcran() {
		return modeEcran;
	}

	public void setModeEcran(ModeObjetEcranServeur modeEcran) {
		this.modeEcran = modeEcran;
	}

	public TaMessageEmail getTaMessageEmail() {
		return taMessageEmail;
	}

	public void setTaMessageEmail(TaMessageEmail taTiers) {
		this.taMessageEmail = taTiers;
	}

	public TaEmail getTaEmail() {
		return taEmail;
	}

	public void setTaEmail(TaEmail taEmail) {
		this.taEmail = taEmail;
	}

	public TaTEmail getTaTEmail() {
		return taTEmail;
	}

	public void setTaTEmail(TaTEmail taTEmail) {
		this.taTEmail = taTEmail;
	}

	
	public StreamedContent getStreamedPieceJointe() {
//		InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/demo/images/optimus.jpg");
//        file = new DefaultStreamedContent(stream, "image/jpg", "downloaded_optimus.jpg");
		InputStream stream = new ByteArrayInputStream(selectedTaPieceJointeEmailDTO.getFichier()); 
		streamedPieceJointe = new DefaultStreamedContent(stream,null,selectedTaPieceJointeEmailDTO.getNomFichier());
		return streamedPieceJointe;
	}

	public TaPieceJointeEmailDTO getSelectedTaPieceJointeEmailDTO() {
		return selectedTaPieceJointeEmailDTO;
	}

	public void setSelectedTaPieceJointeEmailDTO(TaPieceJointeEmailDTO selectedTaPieceJointeEmailDTO) {
		this.selectedTaPieceJointeEmailDTO = selectedTaPieceJointeEmailDTO;
	}
	
	public void actDialogEmail(ActionEvent actionEvent) {
	    
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", false);
        options.put("closable", false);
        options.put("resizable", true);
        options.put("contentHeight", 710);
        options.put("contentWidth", "100%");
        //options.put("contentHeight", "100%");
        options.put("width", 1024);
        
        //Map<String,List<String>> params = null;
        Map<String,List<String>> params = new HashMap<String,List<String>>();
        List<String> list = new ArrayList<String>();
        list.add(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION));
        params.put("modeEcranDefaut", list);
        
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		
		EmailParam email = new EmailParam();
		email.setEmailExpediteur(null);
//		email.setNomExpediteur(taInfoEntrepriseService.findInstance().getNomInfoEntreprise()); 
//		email.setSubject(taInfoEntrepriseService.findInstance().getNomInfoEntreprise()+" Facture "+masterEntity.getCodeDocument()); 
//		email.setBodyPlain("Votre facture "+masterEntity.getCodeDocument());
//		email.setBodyHtml("Votre facture "+masterEntity.getCodeDocument());
//		email.setDestinataires(new String[]{masterEntity.getTaTiers().getTaEmail().getAdresseEmail()});
//		email.setEmailDestinataires(new TaEmail[]{masterEntity.getTaTiers().getTaEmail()});
		
//		EmailPieceJointeParam pj1 = new EmailPieceJointeParam();
//		pj1.setFichier(new File(((ITaFactureServiceRemote)taDocumentService).generePDF(masterEntity.getIdDocument())));
//		pj1.setTypeMime("pdf");
//		pj1.setNomFichier(pj1.getFichier().getName());
//		email.setPiecesJointes(
//				new EmailPieceJointeParam[]{pj1}
//				);
		
//		sessionMap.put("email", email);
        
        PrimeFaces.current().dialog().openDynamic("/dialog_email", options, params);
    
	}
	
	public void handleReturnDialogEmail(SelectEvent event) {
		if(event!=null && event.getObject()!=null) {
			
			TaMessageEmail j = (TaMessageEmail) event.getObject();
			
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Email", 
					"Email envoyée "
					)); 
		}
	}

	public GoogleBean getGoogleBean() {
		return googleBean;
	}

	public void setGoogleBean(GoogleBean googleBean) {
		this.googleBean = googleBean;
	}

	public MSBean getMsBean() {
		return msBean;
	}

	public void setMsBean(MSBean msBean) {
		this.msBean = msBean;
	}

	public StripeBean getStripeBean() {
		return stripeBean;
	}

	public void setStripeBean(StripeBean stripeBean) {
		this.stripeBean = stripeBean;
	}

}  
  