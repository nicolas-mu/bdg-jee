package fr.legrain.bdg.webapp.documents;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.FinderException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import org.apache.commons.beanutils.PropertyUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

import fr.legrain.article.dto.TaArticleDTO;
import fr.legrain.article.model.TaArticle;
import fr.legrain.article.model.TaCoefficientUnite;
import fr.legrain.article.model.TaPrix;
import fr.legrain.article.model.TaRapportUnite;
import fr.legrain.article.model.TaTva;
import fr.legrain.article.model.TaUnite;
import fr.legrain.article.titretransport.model.TaTitreTransport;
import fr.legrain.bdg.documents.service.remote.ITaInfosProformaServiceRemote;
import fr.legrain.bdg.documents.service.remote.ITaLProformaServiceRemote;
import fr.legrain.bdg.documents.service.remote.ITaProformaServiceRemote;
import fr.legrain.bdg.dossier.service.remote.ITaPreferencesServiceRemote;
import fr.legrain.bdg.lib.osgi.Const;
import fr.legrain.bdg.lib.osgi.ConstActionInterne;
import fr.legrain.bdg.lib.server.osgi.BdgProperties;
import fr.legrain.bdg.model.mapping.LgrDozerMapper;
import fr.legrain.bdg.webapp.ConstWeb;
import fr.legrain.bdg.webapp.SessionListener;
import fr.legrain.bdg.webapp.app.EditionParam;
import fr.legrain.bdg.webapp.app.LgrTab;
import fr.legrain.birt.AnalyseFileReport;
import fr.legrain.document.dto.TaLProformaDTO;
import fr.legrain.document.dto.TaLigneALigneDTO;
import fr.legrain.document.dto.TaLigneALigneEcheanceDTO;
import fr.legrain.document.dto.TaProformaDTO;
import fr.legrain.document.dto.TaRDocumentDTO;
import fr.legrain.document.model.TaDevis;
import fr.legrain.document.model.TaEtat;
import fr.legrain.document.model.TaInfosProforma;
import fr.legrain.document.model.TaLProforma;
import fr.legrain.document.model.TaLigneALigneEcheance;
import fr.legrain.document.model.TaMiseADisposition;
import fr.legrain.document.model.TaProforma;
import fr.legrain.document.model.TaTLigne;
import fr.legrain.document.model.TaTPaiement;
import fr.legrain.dossier.model.TaPreferences;
import fr.legrain.droits.model.IModulesProgramme;
import fr.legrain.edition.dto.TaEditionDTO;
import fr.legrain.edition.model.TaActionEdition;
import fr.legrain.edition.model.TaEdition;
import fr.legrain.email.model.TaMessageEmail;
import fr.legrain.lib.data.EnumModeObjet;
import fr.legrain.lib.data.LibConversion;
import fr.legrain.lib.data.LibDate;
import fr.legrain.tiers.dto.AdresseInfosFacturationDTO;
import fr.legrain.tiers.dto.AdresseInfosLivraisonDTO;
import fr.legrain.tiers.dto.TaCPaiementDTO;
import fr.legrain.tiers.dto.TaTiersDTO;
import fr.legrain.tiers.model.TaCPaiement;
import fr.legrain.tiers.model.TaTCPaiement;
import fr.legrain.tiers.model.TaTTvaDoc;
import fr.legrain.tiers.model.TaTiers;

@Named
@ViewScoped  
public class ProformaController extends AbstractDocumentController<TaProforma,TaProformaDTO,TaLProforma,TaLProformaDTO,TaLProformaDTOJSF,TaInfosProforma> {  



	private @EJB ITaProformaServiceRemote taProformaService;
	private @EJB ITaInfosProformaServiceRemote taInfosDocumentService;
	private @EJB ITaLProformaServiceRemote taLProformaService;


	private String theme="defaultTheme";
	private String librairie="bdgFactTheme1";

	public ProformaController() {  
		//values = getValues();
	}  

	@PostConstruct
	public void postConstruct(){
		super.postConstruct();
		listePreferences= taPreferencesService.findByGroupe("proforma");
		nomDocument=" Facture proforma";
		setTaDocumentService(taProformaService);
		setTaLDocumentService(taLProformaService);
		setTaInfosDocumentService(taInfosDocumentService);
		
		initListeEdition();
		
		stepSynthese = "idSyntheseProforma";
		stepEntete = "idEnteteProforma";
		stepLignes = "idLignesProforma";
		stepTotaux = "idTotauxFormProforma";
		stepValidation = "idValidationFormProforma";
		idMenuBouton = "form:tabView:idMenuBoutonProforma";
		wvEcran = LgrTab.WV_TAB_PROFORMA;
		classeCssDataTableLigne = "myclassProforma";
		refresh();
		initChoixEdition();
		
		theme1();
	}
	
	public void initChoixEdition() {
		choixEdition = false;
		List<TaEdition> listeEditionDisponible = taEditionService.rechercheEditionDisponible("", ConstActionInterne.EDITION_PROFORMA_DEFAUT,null);
		
		if(listeEditionDisponible!=null && !listeEditionDisponible.isEmpty() && listeEditionDisponible.size()>1) {
			choixEdition = true;
		}
	}

	public void refresh(){
//		values = taProformaService.selectAllDTO();
		values = taProformaService.findAllLight();
		valuesLigne = IHMmodel();
		modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
	}

	public List<TaLProformaDTOJSF> IHMmodel() {
		LinkedList<TaLProforma> ldao = new LinkedList<TaLProforma>();
		LinkedList<TaLProformaDTOJSF> lswt = new LinkedList<TaLProformaDTOJSF>();

		if(masterEntity!=null && !masterEntity.getLignes().isEmpty()) {

			ldao.addAll(masterEntity.getLignes());

			LgrDozerMapper<TaLProforma,TaLProformaDTO> mapper = new LgrDozerMapper<TaLProforma,TaLProformaDTO>();
			TaLProformaDTO dto = null;
			for (TaLProforma o : ldao) {
				TaLProformaDTOJSF t = new TaLProformaDTOJSF();
				dto = (TaLProformaDTO) mapper.map(o, TaLProformaDTO.class);
				t.setDto(dto);
//				t.setTaLot(o.getTaLot()); //@@ champs à ajouter aux devis
				t.setTaArticle(o.getTaArticle());
				if(o.getTaArticle()!=null){
					t.setTaRapport(o.getTaArticle().getTaRapportUnite());
				}
//				t.setTaEntrepot(o.getTaEntrepot()); //@@ champs à ajouter aux devis
				if(o.getU1LDocument()!=null) {
					t.setTaUnite1(new TaUnite());
					t.getTaUnite1().setCodeUnite(o.getU1LDocument());
				}
				if(o.getU2LDocument()!=null) {
					t.setTaUnite2(new TaUnite());
					t.getTaUnite2().setCodeUnite(o.getU2LDocument());
					t.setTaCoefficientUnite(recupCoefficientUnite(t.getDto().getU1LDocument(),t.getDto().getU2LDocument()));
				}
				if(o.getCodeTitreTransport()!=null) {
					try {
						t.setTaTitreTransport(taTitreTransportService.findByCode(o.getCodeTitreTransport()));
						if(t.getTaArticle()!=null && t.getTaArticle().getTaRTaTitreTransport()!=null)
							t.getTaArticle().getTaRTaTitreTransport().setTaTitreTransport(t.getTaTitreTransport());
					} catch (FinderException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}				

				t.setTaREtatLigneDocument(o.getTaREtatLigneDocument());
				if(o.getTaREtatLigneDocument()!=null && o.getTaREtatLigneDocument().getTaEtat()!=null)
					t.setCodeEtat(o.getTaREtatLigneDocument().getTaEtat().getCodeEtat());
				t.updateDTO(false);
				List<TaRDocumentDTO>  r=rechercheSiDocLie();
				List<TaLigneALigneDTO>  l=rechercheSiLigneDocLie(t);
				List<TaLigneALigneEcheanceDTO>  le=rechercheSiLigneEcheanceDocLie(t);
				if(le!=null) {
					for (TaLigneALigneEcheanceDTO ligne : le) {
							if(t.getLigneAbonnement()==null)t.setLigneAbonnement(new LinkedList<>());
							t.getLigneAbonnement().add(ligne);
						}
				}
				if(l!=null) {
					for (TaLigneALigneDTO ligne : l) {
						if(ligne.getIdLigneSrc().equals(ligne.getIdLDocumentSrc())) {
							if(t.getLigneLieeFils()==null)t.setLigneLieeFils(new LinkedList<>());
							t.getLigneLieeFils().add(ligne);
						}
						else { 
							if(t.getLigneLieeMere()==null)t.setLigneLieeMere(new LinkedList<>());
							t.getLigneLieeMere().add(ligne);
						}	
						if(ligne.getEtat()!=null) {
//							t.setCodeEtat(ligne.getEtat());
							t.setLibelleEtat(" - Liée au document "+ligne.getCodeDocumentDest());
						}
					}
				}else
					if(r!=null) {
						for (TaRDocumentDTO ligne : r) {
//							t.setCodeEtat("*****");
							t.setLibelleEtat("Liée au document "+ligne.getCodeDocumentDest());
						}
					}
				
				lswt.add(t);

			}

		}
		return lswt;
	}
	
//	public String handleFlow(FlowEvent event) {
//		try{
//			currentStepId = event.getOldStep();
//			String stepToGo = event.getNewStep();
//
//			if(modeEcranLigne.dataSetEnModif())
//				actEnregistrerLigne(null);
//
//			if(currentStepId.equals(stepEntete) && stepToGo.equals(stepLignes)) {
//				mapperUIToModel.map(selectedDocumentDTO, masterEntity);
//			}
//
//
//			currentStepId=event.getNewStep();
//			PrimeFaces.current().ajax().update("form:tabView:idMenuBoutonProforma");
//			return currentStepId;
//		} catch (Exception e) {
//			FacesContext context = FacesContext.getCurrentInstance();  
//			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Proforma", e.getMessage()));
//			return event.getOldStep();
//		}
//	}

	
//	public void actAutoInsererLigne(ActionEvent actionEvent) /*throws Exception*/ {
//		boolean existeDeja=false;
//		if(insertAuto) {
//			for (ILigneDocumentJSF ligne : valuesLigne) {
//				if(ligne.ligneEstVide())existeDeja=true;
//			}
//			if(!existeDeja){	
//			actInsererLigne(actionEvent);
//			
//			String oncomplete="jQuery('.myclass .ui-datatable-data tr').last().find('span.ui-icon-pencil').each(function(){jQuery(this).click()});";
//			PrimeFaces.current().executeScript(oncomplete);
//			}
//		} else {
////			throw new Exception();
//		}
//	}

	public void initListeEdition() {
		listeEdition = taEditionService.findByCodeTypeDTOAvecActionsEdition(TaProforma.TYPE_DOC.toUpperCase());
		TaEditionDTO editionDefautNulle = new TaEditionDTO();
		editionDefautNulle.setLibelleEdition("Proforma modèle par défaut (V0220)");
		listeEdition.add(editionDefautNulle);
	}
	
	public void envoiMailEdition(TaEditionDTO edition, String modeEdition, String pourClient) {
		 Map<String,Object> options = new HashMap<String, Object>();
	       
	        
	        Map<String,List<String>> params = new HashMap<String,List<String>>();
	        List<String> list = new ArrayList<String>();
	        list.add(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION));
	        params.put("modeEcranDefaut", list);
	        
	        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			Map<String, Object> mapParametreEdition = null;
			
	
				
			mapParametreEdition = new HashMap<>();
			boolean editionClient = false;	
//			String modeEdition = (String)actionEvent.getComponent().getAttributes().get("mode_edition");
//			String pourClient = (String)actionEvent.getComponent().getAttributes().get("pour_client");
			editionClient = LibConversion.StringToBoolean(pourClient,false);
			mapParametreEdition.put("editionClient", editionClient);
			
			List<TaPreferences> liste= taPreferencesService.findByGroupe("proforma");
			mapParametreEdition.put("preferences", liste);
			mapParametreEdition.put("gestionLot", masterEntity.getGestionLot());
			  

//			if(!editionClient) {
//				switch (modeEdition) {
//				case "BROUILLON":
//					if(masterEntity.getResteAReglerComplet().compareTo(BigDecimal.ZERO)==0) {
//						mapParametreEdition.put("mode", "BROUILLONAPAYER");
//					} else {
//						mapParametreEdition.put("mode", "BROUILLON");
//					}
//					break;
//				case "DUPLICATA":
//					if(masterEntity.getResteAReglerComplet().compareTo(BigDecimal.ZERO)==0) {
//						mapParametreEdition.put("mode", "DUPLICATAPAYER");
//					} else {
//						mapParametreEdition.put("mode", "DUPLICATA");
//					}
//					break;
//				case "PAYER":
//					if(masterEntity.getResteAReglerComplet().compareTo(BigDecimal.ZERO)==0) {
//						mapParametreEdition.put("mode", "PAYER");
//					} else {
//						mapParametreEdition.put("mode", "PAYER");
//					}
//					break;
//	
//				default:
//					break;
//				}
//			} else {
//				if(masterEntity.getResteAReglerComplet().compareTo(BigDecimal.ZERO)==0) {
//					mapParametreEdition.put("mode", "CLIENTAPAYER");
//				} else {
//					mapParametreEdition.put("mode", "CLIENT");
//				}
//			}
			
			
			EditionParam editionParam = new EditionParam();
			editionParam.setIdActionInterne(ConstActionInterne.EDITION_PROFORMA_DEFAUT);
			editionParam.setMapParametreEdition(mapParametreEdition);
			
			sessionMap.put(EditionParam.NOM_OBJET_EN_SESSION, editionParam);
	        
			
	        //PrimeFaces.current().dialog().openDynamic("/dialog_choix_edition", options, params);
			
			//ICI s'arrete la partie actDialogImprimer
			
			//ICI COMMENCE LA PARTIE handleReturnDialogImprimer
			String pdf="";
			TaEdition taEdition = null;
			if(edition == null) {//si aucune edition n'est choisie
				TaActionEdition action = new TaActionEdition();
				action.setCodeAction(TaActionEdition.code_mail);
				
				taEdition = taEditionService.rechercheEditionActionDefaut(null,action, TaProforma.TYPE_DOC);
				
			}else {//si une édition est choisie
				
				 if(edition.getId() != null) {//si ce n'est pas l'édition defaut programme
					 try {
							taEdition = taEditionService.findById(edition.getId());
						} catch (FinderException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				 } 
				
				if(taEdition == null) {
					taEdition = taEditionService.rechercheEditionDisponibleProgrammeDefaut("", ConstActionInterne.EDITION_PROFORMA_DEFAUT).get(0);
				}
				
				
			}
			//On rempli selectedEdition qui va être utiliser dans AbstractDocumentController.actDialogEmailSelectedEdition
			if(taEdition!=null) {
				
				taEdition.setMapParametreEdition(mapParametreEdition);
				selectedEdition = taEdition;

				
			}
	}
	
	public void actEnregistrerLigne(ActionEvent actionEvent) {

		try {
			selectedLigneJSF.updateDTO(false);
//			if(selectedLigneJSF.getDto().getIdLDocument()!=null &&  selectedLigneJSF.getDto().getIdLDocument()!=0) {
//				masterEntityLigne=rechercheLigne(selectedLigneJSF.getDto().getIdLDocument());
//			}
			if(masterEntityLigne.getTaArticle()!=null && selectedLigneJSF.getDto().getPrixULDocument()==null) {
				masterEntityLigne.setPrixULDocument(BigDecimal.ZERO);
				selectedLigneJSF.getDto().setPrixULDocument(BigDecimal.ZERO);
			}
			
			LgrDozerMapper<TaLProformaDTO,TaLProforma> mapper = new LgrDozerMapper<TaLProformaDTO,TaLProforma>();
			mapper.map((TaLProformaDTO) selectedLigneJSF.getDto(),masterEntityLigne);
	
			//masterEntityLigne.setTaArticle(selectedLigneJSF.getTaArticle());
			
//			masterEntityLigne.setTaEntrepot(selectedLigneJSF.getTaEntrepot());//@@ champs à ajouter aux devis
//			if(selectedLigneJSF.getDto()!=null ) {
//				TaLot l = new TaLot();
//				if(selectedLigneJSF.getTaLot()!=null)l=selectedLigneJSF.getTaLot();
////				if(selectedLigneJSF.getDto().getDluo()!=null) {//@@ champs à ajouter aux devis
////					l.setDluo(selectedLigneJSF.getDto().getDluo());//@@ champs à ajouter aux devis
////				}
////				l.setNumLot(selectedLigneJSF.getDto().getNumLot());//@@ champs à ajouter aux devis
//				l.setUnite1(masterEntityLigne.getU1LDocument());
//				l.setUnite2(masterEntityLigne.getU2LDocument());
//				l.setTaArticle(masterEntityLigne.getTaArticle());
//				if(selectedLigneJSF.getTaRapport()!=null){
//					l.setNbDecimal(selectedLigneJSF.getTaRapport().getNbDecimal());
//					l.setSens(selectedLigneJSF.getTaRapport().getSens());
//					l.setRapport(selectedLigneJSF.getTaRapport().getRapport());
//				}
////				masterEntityLigne.setTaLot(l);//@@ champs à ajouter aux devis
//			}
			
			if(masterEntityLigne.getCodeTvaLDocument()!=null && !masterEntityLigne.getCodeTvaLDocument().isEmpty()) {
				TaTva tva=taTvaService.findByCode(masterEntityLigne.getCodeTvaLDocument());
				if(tva!=null)
					masterEntityLigne.setLibTvaLDocument(tva.getLibelleTva());
			}


			masterEntity.enregistreLigne(masterEntityLigne);
			if(!masterEntity.getLignes().contains(masterEntityLigne))
				masterEntity.addLigne(masterEntityLigne);	
			
//			taBonReceptionService.calculeTvaEtTotaux(masterEntity);
			masterEntity.calculeTvaEtTotaux();
			mapperModelToUI.map(masterEntity, selectedDocumentDTO);
			
//			modeEcranLigne.setMode(EnumModeObjet.C_MO_INSERTION);
			modeEcranLigne.setMode(EnumModeObjet.C_MO_CONSULTATION);

		} catch (Exception e) {
			e.printStackTrace();
		}

		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proforma", "actEnregisterLigne")); 
		}
	}

//	public TaLProforma rechercheLigne(int id){
//		TaLProforma obj=masterEntityLigne;
//		for (TaLProforma ligne : masterEntity.getLignes()) {
//			if(ligne.getIdLDocument()==id)
//				obj=ligne;
//		}
//		return obj;
//	}
//	
//	public TaLProforma rechercheLigneNumLigne(int numLigne){
//		TaLProforma obj=masterEntityLigne;
//		for (TaLProforma ligne : masterEntity.getLignes()) {
//			if(ligne.getNumLigneLDocument()==numLigne)
//				obj=ligne;
//		}
//		return obj;
//	}
//	
//	public void actSupprimerLigne() {
//		//http://stackoverflow.com/questions/18358467/fsetpropertyactionlistener-always-setting-null
//		actSupprimerLigne(null);
//	}
//	
//	public void actSupprimerLigne(ActionEvent actionEvent) {
//		//http://stackoverflow.com/questions/18358467/fsetpropertyactionlistener-always-setting-null
//		try {
//			
//			//Integer idLigne = (Integer) actionEvent.getComponent().getAttributes().get("idLigne");
//			
//			valuesLigne.remove(selectedLigneJSF);
//			
////			for (TaLProforma lbr : masterEntity.getLignes()) {
////				if(selectedLigneJSF.getDto().getIdLDocument()==lbr.getIdLDocument()) {
////					masterEntityLigne = lbr;
////				}
////			}
////			masterEntityLigne =rechercheLigne(selectedLigneJSF.getDto().getIdLDocument());
//			
//			if(selectedLigneJSF.getDto().getIdLDocument()!=null && selectedLigneJSF.getDto().getIdLDocument()!=0) {
//				masterEntityLigne =rechercheLigne(selectedLigneJSF.getDto().getIdLDocument());
//			} else {
//				masterEntityLigne =rechercheLigneNumLigne(selectedLigneJSF.getDto().getNumLigneLDocument());
//			}
//
//			masterEntityLigne.setTaDocument(null);	
//			masterEntity.removeLigne(masterEntityLigne);
//			masterEntity.calculeTvaEtTotaux();
//			valuesLigne=IHMmodel();
//			mapperModelToUI.map(masterEntity, selectedDocumentDTO);
//			
//			modeEcranLigne.setMode(EnumModeObjet.C_MO_CONSULTATION);
//	
//			if(ConstWeb.DEBUG) {
//				FacesContext context = FacesContext.getCurrentInstance();  
//				context.addMessage(null, new FacesMessage("Proforma", "actSupprimerLigne")); 
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	
//	
//	public void onRowCancel(RowEditEvent event) {
//		//((TaLProformaDTOJSF) event.getObject()).setAutoInsert(false);
////        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Car) event.getObject()).getId());
////        FacesContext.getCurrentInstance().addMessage(null, msg);
////		if(event.getObject()!=null){
////			valuesLigne.remove(event.getObject());
////			if(masterEntityLigne!=null)masterEntity.getLignes().remove(masterEntityLigne);
////		}
//		selectionLigne((TaLProformaDTOJSF) event.getObject());
//		actAnnulerLigne(null);
//    }
//	
//	public void onRowEditInit(RowEditEvent event) {
//		AjaxBehaviorEvent evt = (AjaxBehaviorEvent)event;
//		DataTable table = (DataTable) evt.getSource();
//		int activeRow = table.getRowIndex()+1;
//		if(table.getRowCount() == activeRow) {
//			//derniere ligne
//			setInsertAuto(modeEcran.dataSetEnInsertion());
//		} else {
//			setInsertAuto(false);
//		}
//		if(event.getObject()!=null && !event.getObject().equals(selectedLigneJSF))
//			selectedLigneJSF=(TaLProformaDTOJSF) event.getObject();
//		actModifierLigne(null);
//}
//	
//	public void onRowSelectLigne(SelectEvent event) { 
//		if(event.getObject()!=null)
//			selectionLigne((TaLProformaDTOJSF) event.getObject());		
//	}
//	
//	public void selectionLigne(TaLProformaDTOJSF ligne){
//		selectedLigneJSF=ligne;
//		if(masterEntity.getIdDocument()!=0 && selectedLigneJSF!=null && selectedLigneJSF.getDto().getIdLDocument()!=null)
//			masterEntityLigne=rechercheLigne(selectedLigneJSF.getDto().getIdLDocument());
//	}

	public void actAnnuler(ActionEvent actionEvent) {
		try {
			switch (modeEcran.getMode()) {
			case C_MO_INSERTION:
				if(selectedDocumentDTO.getCodeDocument()!=null) {
					taProformaService.annuleCode(selectedDocumentDTO.getCodeDocument());
				}
				masterEntity=new TaProforma();
				selectedDocumentDTO=new TaProformaDTO();
				
				if(values.size()>0)	selectedDocumentDTO = values.get(0);

				onRowSelect(null);
				
				valuesLigne=IHMmodel();
				initInfosDocument();
				break;
			case C_MO_EDITION:
				if(selectedDocumentDTO.getId()!=null && selectedDocumentDTO.getId()!=0){
					masterEntity = taProformaService.findByIDFetch(selectedDocumentDTO.getId());
					selectedDocumentDTO=taProformaService.findByIdDTO(selectedDocumentDTO.getId());
				}				
				break;

			default:
				break;
			}			
				
		modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
		modeEcranLigne.setMode(EnumModeObjet.C_MO_CONSULTATION);
		
		updateTab();
		modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);

		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proforma", "actAnnuler")); 
		}
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void autoCompleteMapUIToDTO() {
//		if(taTiers!=null) {
//			validateUIField(Const.C_CODE_TIERS,taTiers);
//			selectedDocumentDTO.setCodeTiers(taTiers.getCodeTiers());
//		}
		if(taTiersDTO!=null) {
			validateUIField(Const.C_CODE_TIERS,taTiersDTO);
			selectedDocumentDTO.setCodeTiers(taTiersDTO.getCodeTiers());
		}
		if(taTPaiement!=null) {
			validateUIField(Const.C_CODE_T_PAIEMENT,taTPaiement.getCodeTPaiement());
			selectedDocumentDTO.setCodeTPaiement(taTPaiement.getCodeTPaiement());
		}
		if(taCPaiementDoc!=null) {
			validateUIField(Const.C_CODE_C_PAIEMENT,taCPaiementDoc.getCodeCPaiement());
			selectedCPaiement.setCodeCPaiement(taCPaiementDoc.getCodeCPaiement());
		}

		if(taTTvaDoc!=null) {
			validateUIField(Const.C_CODE_T_TVA_DOC,taTTvaDoc.getCodeTTvaDoc());
			selectedDocumentDTO.setCodeTTvaDoc(taTTvaDoc.getCodeTTvaDoc());
		}
//		if(selectedEtatDocument!=null) {
//			validateUIField(Const.C_CODE_ETAT,selectedEtatDocument.getCodeEtat());
//			selectedDocumentDTO.setCodeEtat(selectedEtatDocument.getCodeEtat());
//			masterEntity.setTaEtat(selectedEtatDocument);
//		}
	}

	public void autoCompleteMapDTOtoUI() {
		try {
			taTiers = null;
			taTiersDTO = null;
			taTPaiement = null;
			taTTvaDoc = null;
			taCPaiementDoc = null;
			//			taTEntite = null;
			if(selectedDocumentDTO.getCodeTiers()!=null && !selectedDocumentDTO.getCodeTiers().equals("")) {
				taTiers = taTiersService.findByCode(selectedDocumentDTO.getCodeTiers());
				taTiersDTO = mapperModelToUITiers.map(taTiers, TaTiersDTO.class);
			}
			if(selectedDocumentDTO.getCodeTPaiement()!=null && !selectedDocumentDTO.getCodeTPaiement().equals("")) {
				taTPaiement = taTPaiementService.findByCode(selectedDocumentDTO.getCodeTPaiement());
			}
			if(selectedCPaiement.getCodeCPaiement()!=null && !selectedCPaiement.getCodeCPaiement().equals("")) {
				taCPaiementDoc = taCPaiementService.findByCode(selectedCPaiement.getCodeCPaiement());
			}			
			
			if(selectedDocumentDTO.getCodeTTvaDoc()!=null && !selectedDocumentDTO.getCodeTTvaDoc().equals("")) {
				taTTvaDoc = taTTvaDocService.findByCode(selectedDocumentDTO.getCodeTTvaDoc());
			}
			//rajouté temporairement pour aller vite, sera remplacé par la gestion des réglements multiples
			factureReglee=(masterEntity.getTaRReglementLiaison()!=null && masterEntity.getTaRReglementLiaison().getTaReglement()!=null
					&& masterEntity.getTaRReglementLiaison().getTaReglement().getIdDocument()!=0);
			if(masterEntity.getTaRReglementLiaison()!=null && masterEntity.getTaRReglementLiaison().getTaReglement()!=null){
				taReglement=masterEntity.getTaRReglementLiaison().getTaReglement();

				if(taReglement.getTaTPaiement()!=null && taReglement.getTaTPaiement().getIdTPaiement()!=0)
					taTPaiement=taTPaiementService.findById(taReglement.getTaTPaiement().getIdTPaiement());
				if(taTPaiement!=null){
					selectedDocumentDTO.setCodeTPaiement(taTPaiement.getCodeTPaiement());
				}
				selectedDocumentDTO.setLibelleReglement(taReglement.getLibelleDocument());
				selectedDocumentDTO.setMontantReglement(masterEntity.getTaRReglementLiaison().getAffectation());
			}else{
				selectedDocumentDTO.setLibelleReglement("");
				selectedDocumentDTO.setMontantReglement(BigDecimal.ZERO);
			}
			masterEntity.calculRegleDocument();
			factureARegler=masterEntity.getResteAReglerComplet().compareTo(BigDecimal.ZERO)!=0;					
		} catch (FinderException e) {
			e.printStackTrace();
		}
	}

//	public void onRowReorder(ReorderEvent event) {
//		int i=1;
//		
//		for (ILigneDocumentJSF l : valuesLigne) {
//			l.getDto().setNumLigneLDocument(i);
//			if(l.getDto().getIdLDocument()!=null &&  l.getDto().getIdLDocument()!=0) {
//				masterEntityLigne=rechercheLigne(l.getDto().getIdLDocument());
//			}else if(l.getDto().getNumLigneLDocument()!=null/* &&  selectedTaLBonReceptionDTOJSF.getDto().getNumLigneLDocument()!=0*/) {
//				masterEntityLigne = rechercheLigneNumLigne(l.getDto().getNumLigneLDocument());
//			}
//			//change le num ligne et remplace l'odre des lignes dans la liste des lignes du document
//			masterEntityLigne.setNumLigneLDocument(i);
//			masterEntity.getLignes().remove(masterEntityLigne);
//			masterEntity.getLignes().add(masterEntityLigne.getNumLigneLDocument()-1, masterEntityLigne);
//			i++;
//		}
//		actModifier();
//		if(valuesLigne!=null && !valuesLigne.isEmpty())
//			selectionLigne(valuesLigne.get(0));
//	}
	
	/**
	 * @author yann
	 * On surcharge la méthode actModifier pour éviter le vérouillage quand mis a dispo
	 */
	public void actModifier(ActionEvent actionEvent) {
		//if(verifSiEstModifiable(masterEntity)) {
			if(!modeEcran.getMode().equals(EnumModeObjet.C_MO_EDITION) && !modeEcran.getMode().equals(EnumModeObjet.C_MO_INSERTION))
				modeEcran.setMode(EnumModeObjet.C_MO_EDITION);
			docEnregistre=false;
			masterEntity.setLegrain(true);
			initialisePositionBoutonWizard();
			if(ConstWeb.DEBUG) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage("Facture", "actModifier")); 	 
			}
			ecranSynthese=modeEcran.getMode().equals(EnumModeObjet.C_MO_CONSULTATION);
			changementStepWizard(true);
		//}
	}
	
	public void actEnregistrer(ActionEvent actionEvent) {		
		try {
			if(masterEntity.getIdDocument()==0) {
				taGenCodeExService.libereVerrouTout(new ArrayList<String>(SessionListener.getSessions().keySet()));
				Map<String, String> paramsCode = new LinkedHashMap<>();
				paramsCode.put(Const.C_DATEDOCUMENT, LibDate.dateToString(selectedDocumentDTO.getDateDocument()));

				if(selectedDocumentDTO.getCodeDocument()!=null) {
					taProformaService.annuleCode(selectedDocumentDTO.getCodeDocument());
				}			
				selectedDocumentDTO.setCodeDocument(taProformaService.genereCode(paramsCode));
			}
			
			masterEntity.calculeTvaEtTotaux();
			autoCompleteMapUIToDTO();
			validateDocumentAvantEnregistrement(selectedDocumentDTO);
			mapperUIToModel.map(selectedDocumentDTO, masterEntity);

			
			initInfosDocument();			
			mapperUIToModelDocumentVersInfosDoc.map(masterEntity, taInfosDocument);
			taInfosDocument.setNomTiers(selectedDocumentDTO.getNomTiers());
			taInfosDocument.setCodeTTvaDoc(selectedDocumentDTO.getCodeTTvaDoc());
			
			mapperUIToModelAdresseFactVersInfosDoc.map((AdresseInfosFacturationDTO) selectedAdresseFact, taInfosDocument);
			mapperUIToModelAdresseLivVersInfosDoc.map((AdresseInfosLivraisonDTO) selectedAdresseLiv, taInfosDocument);						
			mapperUIToModelCPaiementVersInfosDoc.map((TaCPaiementDTO) selectedCPaiement, taInfosDocument);
			
//			//vérifier remplissage du codeTTva
//			if(!((IStructuredSelection)viewerComboLocalisationTVA.getSelection()).isEmpty()) {
//				String codeTTvaDoc = ((TaTTvaDoc)((IStructuredSelection)viewerComboLocalisationTVA.getSelection()).getFirstElement()).getCodeTTvaDoc();
//				taInfosDocument.setCodeTTvaDoc(codeTTvaDoc);
//			}			
			
			TaTLigne typeLigneCommentaire =  taTLigneService.findByCode(Const.C_CODE_T_LIGNE_C);
			masterEntity.setLegrain(false);
			List<TaLProforma> listeLigneVide = new ArrayList<TaLProforma>(); 
			for (TaLProforma ligne : masterEntity.getLignes()) {
				ligne.setLegrain(false);
				if(ligne.ligneEstVide() && ligne.getNumLigneLDocument().compareTo(masterEntity.getLignes().size())==0) {
					listeLigneVide.add(ligne);
				} else {
					if(ligne.getTaArticle()==null) {
						ligne.setTaTLigne(typeLigneCommentaire);
					}
				}
			}
			
			//supression des lignes vides
			for (TaLProforma taLBonReception : listeLigneVide) {
				masterEntity.getLignes().remove(taLBonReception);
			}
			
			//suppression des liaisons entre ligne doc et ligne d'échéance
			for (TaLigneALigneEcheance ligneALigne : listeLigneALigneEcheanceASupprimer) {
				taLigneALigneEcheanceService.remove(ligneALigne);
			}
			
			//isa le 08/11/2016
			//j'ai rajouté cette réinitialisation tant que l'on enlève les lignes vides, sinon génère des trous dans la numérotation des lignes
			masterEntity.reinitialiseNumLignes(0, 1);
			/*
			 * TODO Gérer les mouvements corrrespondant aux lignes 
			 */
			//@@ champs à ajouter aux devis
			//@@ champs à ajouter aux devis
			//@@ champs à ajouter aux devis
//			TaGrMouvStock grpMouvStock = new TaGrMouvStock();
//			if(masterEntity.getTaGrMouvStock()!=null)grpMouvStock=masterEntity.getTaGrMouvStock();
//			grpMouvStock.setCodeGrMouvStock(masterEntity.getCodeDocument());
//			grpMouvStock.setDateGrMouvStock(masterEntity.getDateDocument());
//			grpMouvStock.setLibelleGrMouvStock(masterEntity.getLibelleDocument());
//			grpMouvStock.setTaTypeMouvStock(taTypeMouvementService.findByCode("R"));
//			for (TaLProforma ligne : masterEntity.getLignes()) { //@@ champs à ajouter aux devis
//				TaMouvementStock m = new TaMouvementStock();
//				if(ligne.getTaMouvementStock()!=null)m=ligne.getTaMouvementStock();
//				m.setLibelleStock(ligne.getLibLDocument());
//				m.setDateStock(masterEntity.getDateDocument());
//				m.setEmplacement(ligne.getEmplacementLDocument());
//				m.setTaEntrepot(ligne.getTaEntrepot());
//				if(ligne.getTaLot()!=null){
//					m.setNumLot(ligne.getTaLot().getNumLot());
//				}
//				m.setQte1Stock(ligne.getQteLDocument());
//				m.setQte2Stock(ligne.getQte2LDocument());
//				m.setUn1Stock(ligne.getU1LDocument());
//				m.setUn2Stock(ligne.getU2LDocument());
//				m.setTaGrMouvStock(grpMouvStock);
//				ligne.setTaMouvementStock(m);
//				
//				grpMouvStock.getTaMouvementStockes().add(m);
//			}//@@ champs à ajouter aux devis
//
//			masterEntity.setTaGrMouvStock(grpMouvStock); //@@ champs à ajouter aux devis
			
			
			
			masterEntity = taProformaService.mergeAndFindById(masterEntity,ITaProformaServiceRemote.validationContext);
			taProformaService.annuleCode(selectedDocumentDTO.getCodeDocument());

			
			mapperModelToUI.map(masterEntity, selectedDocumentDTO);
			autoCompleteMapDTOtoUI();
			selectedEtatDocument=null;
			if(masterEntity.getTaREtat()!=null)selectedEtatDocument=masterEntity.getTaREtat().getTaEtat();
			selectedDocumentDTOs=new TaProformaDTO[]{selectedDocumentDTO};

			
			if(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_INSERTION)==0) {
				values.add(selectedDocumentDTO);
			}

			modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
			modeEcranLigne.setMode(EnumModeObjet.C_MO_CONSULTATION);
			
			if(impressionDirect) {
				if(impressionDirectClient) {
					imprimeEdition(null, "CLIENT", "true"); 
				}else {
					imprimeEdition(null, "BROUILLON", "false");
				}
				
			}

			if(envoyeParEmail) {
				envoiMailEdition(null, "CLIENT", "true");
			}
			
			updateTab();
			
			ecranSynthese=modeEcran.getMode().equals(EnumModeObjet.C_MO_CONSULTATION);
			if(wizardDocument!=null)wizardDocument.setStep(stepEntete);
			changementStepWizard(false);

		} catch(Exception e) {
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			//context.addMessage(null, new FacesMessage("Proforma", "actEnregistrer")); 
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Proforma", e.getMessage()));
		}


		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proforma", "actEnregistrer")); 
		}
	}

	public void actInserer(ActionEvent actionEvent) {
		try {
			
			impressionDirectClient = false;
			miseADispositionCompteClient = false;
			envoyeParEmail = false;
			
			
			listePreferences= taPreferencesService.findByGroupe("proforma");
			selectedDocumentDTO = new TaProformaDTO();
			selectedDocumentDTO.setCommentaire(rechercheCommentaireDefautDocument());
			masterEntity = new TaProforma();
			masterEntity.setLegrain(true);
			
			//Tous les documents ont cet état par défaut vue avec philippe le 06/08/2019
//			TaEtat etat = taEtatService.findByCode(TaEtat.ETAT_ENCOURS_NON_TRANSFORME);
			TaEtat etat = taProformaService.rechercheEtatInitialDocument();
			masterEntity.addREtat(etat);
			
			valuesLigne = IHMmodel();
			selectedDocumentDTO.setCodeTTvaDoc("F");
			selectedEtatDocument=null;
			autoCompleteMapDTOtoUI();
			if(masterEntity.getTaREtat()!=null)selectedEtatDocument=masterEntity.getTaREtat().getTaEtat();
			
			initInfosDocument();


//			taGenCodeExService.libereVerrouTout(new ArrayList<String>(SessionListener.getSessions().keySet()));
//			Map<String, String> paramsCode = new LinkedHashMap<>();
//			paramsCode.put(Const.C_DATEDOCUMENT, LibDate.dateToString(selectedDocumentDTO.getDateDocument()));
//
//			
//			if(selectedDocumentDTO.getCodeDocument()!=null) {
//				taProformaService.annuleCode(selectedDocumentDTO.getCodeDocument());
//			}			
//
//			selectedDocumentDTO.setCodeDocument(taProformaService.genereCode(paramsCode));
			
			changementTiers(true);


			modeEcran.setMode(EnumModeObjet.C_MO_INSERTION);
			
			
			TaPreferences nb;
			nb = taPreferencesService.findByGoupeAndCle(ITaPreferencesServiceRemote.GROUPE_PREFERENCES_NB_DECIMALES, ITaPreferencesServiceRemote.NB_DECIMALES_PRIX);
			if(nb!=null && nb.getValeur() != null) {
				masterEntity.setNbDecimalesPrix(LibConversion.stringToInteger(nb.getValeur()));
			}

//			nb = taPreferencesService.findByGoupeAndCle(ITaPreferencesServiceRemote.GROUPE_PREFERENCES_NB_DECIMALES, ITaPreferencesServiceRemote.NB_DECIMALES_QTE);
//			if(nb!=null && nb.getValeur() != null) {
//				masterEntity.setNbDecimalesQte(LibConversion.stringToInteger(nb.getValeur()));
//			}
			
			ecranSynthese=modeEcran.getMode().equals(EnumModeObjet.C_MO_CONSULTATION);
			if(wizardDocument!=null)wizardDocument.setStep(stepEntete);
			changementStepWizard(false);
			
			scrollToTop();
			if(ConstWeb.DEBUG) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage("Avoir", "actInserer"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			//		} catch (FinderException e) {
			//			e.printStackTrace();
		}
	}

//	public void actModifier() {
//		actModifier(null);
//	}

//	public void actModifier(ActionEvent actionEvent) {
//		if(!modeEcran.getMode().equals(EnumModeObjet.C_MO_EDITION) && !modeEcran.getMode().equals(EnumModeObjet.C_MO_INSERTION))
//			modeEcran.setMode(EnumModeObjet.C_MO_EDITION);
//		masterEntity.setLegrain(true);
//		if(ConstWeb.DEBUG) {
//			FacesContext context = FacesContext.getCurrentInstance();  
//			context.addMessage(null, new FacesMessage("Proforma", "actModifier")); 	 
//		}
//	}
//
//	public void actAide(ActionEvent actionEvent) {
//
//		//		PrimeFaces.current().dialog().openDynamic("aide");
//
//		Map<String,Object> options = new HashMap<String, Object>();
//		options.put("modal", true);
//		options.put("draggable", false);
//		options.put("resizable", false);
//		options.put("contentHeight", 320);
//		Map<String,List<String>> params = null;
//		PrimeFaces.current().dialog().openDynamic("aide", options, params);
//
//		if(ConstWeb.DEBUG) {
//			FacesContext context = FacesContext.getCurrentInstance();  
//			context.addMessage(null, new FacesMessage("Proforma", "actAide")); 	
//		}
//	}

	//	public void actAideRetour(ActionEvent actionEvent) {
	//		
	//	}

	public void nouveau(ActionEvent actionEvent) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_PROFORMA);
		b.setTitre("Facture proforma");
		b.setTemplate("documents/proforma.xhtml");
		b.setIdTab(LgrTab.ID_TAB_PROFORMA);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PROFORMA);
		tabsCenter.ajouterOnglet(b);
		b.setParamId("0");

//		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);
		actInserer(actionEvent);

		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proforma", 
					"Nouveau"
					)); 
		}
	} 

//	public void supprimer(ActionEvent actionEvent) {
//		actSupprimer(actionEvent);
//	}
//	
//	public void detail() {
//		detail(null);
//	}
//	
//	public void detail(ActionEvent actionEvent) {
//		onRowSelect(null);
//	}
//	
//	public void actSupprimer() {
//		actSupprimer(null);
//	}

	public void actSupprimer(ActionEvent actionEvent) {
		TaProforma obj = new TaProforma();
		try {
			if(autorisationLiaisonComplete()) {
			if(selectedDocumentDTO!=null && selectedDocumentDTO.getId()!=null){
				obj = taProformaService.findByIDFetch(selectedDocumentDTO.getId());
			}

			taProformaService.remove(obj);
			values.remove(selectedDocumentDTO);

			if(!values.isEmpty()) {
				selectedDocumentDTO = values.get(0);
				selectedDocumentDTOs= new TaProformaDTO[]{selectedDocumentDTO};
				updateTab();
			}else{
				selectedDocumentDTO = new TaProformaDTO();
				selectedDocumentDTOs= new TaProformaDTO[]{};	
			}

			modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
//			updateTab();
			
			if(ConstWeb.DEBUG) {
				FacesContext context = FacesContext.getCurrentInstance();  
				context.addMessage(null, new FacesMessage("Proforma", "actSupprimer"));
			}
			}
		} catch(Exception e) {
			e.printStackTrace();
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Proforma", e.getMessage()));
		}	    
	}

	public void actFermer(ActionEvent actionEvent) {
		//fermeture de l'onglet en JavaScript
		
		switch (modeEcran.getMode()) {
		case C_MO_INSERTION:
			actAnnuler(actionEvent);
			break;
		case C_MO_EDITION:
			actAnnuler(actionEvent);							
			break;

		default:
			break;
		}
	
		selectedDocumentDTO=new TaProformaDTO();
		selectedDocumentDTOs=new TaProformaDTO[]{selectedDocumentDTO};
		updateTab();
		
//		
//		//gestion du filtre de la liste
//        RequestContext requestContext = RequestContext.getCurrentInstance();
//        requestContext.execute("PF('wvDataTableListeProforma').filter()");

		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proforma", "actFermer")); 
		}
	}

	public void actImprimer(ActionEvent actionEvent) {
		if(ConstWeb.DEBUG) {
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Proforma", "actImprimer")); 
		}
		try {
			
			//Fiche_devis.rptdesign&=7332&typeTraite=null&PageBreakTotaux=27&ape=&ParamChoix=choix 1
			// &PageBreakMaxi=36&ParamCorr=null&CoupureLigne=54
			//		&nomEntreprise=EARL DE GRINORD&capital=&rcs=&siret=&__document=doc1469798570882&__format=pdf

			//		FacesContext facesContext = FacesContext.getCurrentInstance();
			//		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);

			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			
			Map<String,Object> mapEdition = new HashMap<String,Object>();
			
			TaProforma doc =taProformaService.findById(selectedDocumentDTO.getId());
			doc.calculeTvaEtTotaux();
			
			mapEdition.put("doc",doc );
			mapEdition.put("taInfoEntreprise", taInfoEntrepriseService.findInstance());
			//mapEdition.put("LibelleJournalTva", taTTvaDoc.getLibelleEdition());
			
			mapEdition.put("lignes", masterEntity.getLignes());
			
			//sessionMap.put("doc", taProformaService.findById(selectedDocumentDTO.getId()));
			Map<String,Object> mapParametreEdition = new HashMap<String,Object>();
			List<TaPreferences> liste= taPreferencesService.findByGroupe("proforma");
			mapParametreEdition.put("preferences", liste);
//			mapParametreEdition.put("gestionLot", gestionLot);
			mapParametreEdition.put("Theme", theme);
			mapParametreEdition.put("Librairie", librairie);
			
			mapEdition.put("param", mapParametreEdition);
			
			sessionMap.put("edition", mapEdition);


			//			session.setAttribute("tiers", taTiersService.findById(selectedDocumentDTO.getId()));
			System.out.println("ProformaController.actImprimer()");
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}    
	
	public void actImprimerListeDesProformas(ActionEvent actionEvent) {
		
		try {
			
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			
			sessionMap.put("listeDesProformas", values);

			} catch (Exception e) {
				e.printStackTrace();
			}
	}    

public void actDialogImprimer(ActionEvent actionEvent) {
	    
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
		Map<String, Object> mapParametreEdition = null;
		
		if(actionEvent!=null){
			
			mapParametreEdition = new HashMap<>();
			boolean editionClient = false;	
			String modeEdition = (String)actionEvent.getComponent().getAttributes().get("mode_edition");
			String pourClient = (String)actionEvent.getComponent().getAttributes().get("pour_client");
			editionClient = LibConversion.StringToBoolean(pourClient,false);
			mapParametreEdition.put("editionClient", editionClient);
			
			List<TaPreferences> liste= taPreferencesService.findByGroupe("proforma");
			mapParametreEdition.put("preferences", liste);
			mapParametreEdition.put("gestionLot", masterEntity.getGestionLot());
			
//			public void actImprimer(ActionEvent actionEvent) {
//				actImprimerGlobal(actionEvent,"CLIENT","CLIENTAPAYER");
//			}    

			if(!editionClient) {
				switch (modeEdition) {
				case "BROUILLON":
					if(masterEntity.getResteAReglerComplet().compareTo(BigDecimal.ZERO)==0) {
						mapParametreEdition.put("mode", "BROUILLONAPAYER");
					} else {
						mapParametreEdition.put("mode", "BROUILLON");
					}
					break;
				case "DUPLICATA":
					if(masterEntity.getResteAReglerComplet().compareTo(BigDecimal.ZERO)==0) {
						mapParametreEdition.put("mode", "DUPLICATAPAYER");
					} else {
						mapParametreEdition.put("mode", "DUPLICATA");
					}
					break;
				case "PAYER":
					if(masterEntity.getResteAReglerComplet().compareTo(BigDecimal.ZERO)==0) {
						mapParametreEdition.put("mode", "PAYER");
					} else {
						mapParametreEdition.put("mode", "PAYER");
					}
					break;
	
				default:
					break;
				}
			} else {
				if(masterEntity.getResteAReglerComplet().compareTo(BigDecimal.ZERO)==0) {
					mapParametreEdition.put("mode", "CLIENTAPAYER");
				} else {
					mapParametreEdition.put("mode", "CLIENT");
				}
			}
		}
		
		EditionParam edition = new EditionParam();
		edition.setIdActionInterne(ConstActionInterne.EDITION_PROFORMA_DEFAUT);
		edition.setMapParametreEdition(mapParametreEdition);
		
		sessionMap.put(EditionParam.NOM_OBJET_EN_SESSION, edition);
        
        PrimeFaces.current().dialog().openDynamic("/dialog_choix_edition", options, params);
		
//		FacesContext context = FacesContext.getCurrentInstance();  
//		context.addMessage(null, new FacesMessage("Tiers", "actAbout")); 	    
	}
	
	public void handleReturnDialogEmail(SelectEvent event) {
		if(event!=null && event.getObject()!=null) {
			TaMessageEmail j = (TaMessageEmail) event.getObject();
			
			//Mise à jour de la mise à dispostion
			if(masterEntity.getTaMiseADisposition()==null) {
				masterEntity.setTaMiseADisposition(new TaMiseADisposition());
			}
			Date dateMiseADispositionClient = new Date();
			
			masterEntity.getTaMiseADisposition().setEnvoyeParEmail(dateMiseADispositionClient);
			masterEntity = taProformaService.mergeAndFindById(masterEntity,ITaProformaServiceRemote.validationContext);
			updateTab();
			
			FacesContext context = FacesContext.getCurrentInstance();  
			context.addMessage(null, new FacesMessage("Email", 
					"Email envoyée "
					));  
		}
	}
	
	public void handleReturnDialogImprimer(SelectEvent event) {
		if(event!=null && event.getObject()!=null) {
			//List<ControleConformiteJSF> listeControle = (List<ControleConformiteJSF>) event.getObject();
			TaEdition taEdition = (TaEdition) event.getObject();

			if(taEdition!=null) {
				System.out.println("ChoixEditionController.actImprimer() "+taEdition.getLibelleEdition());
				List<String> listeResourcesPath = null;
				if(taEdition.getResourcesPath()!=null) {
					listeResourcesPath = new ArrayList<>();
					listeResourcesPath.add(taEdition.getResourcesPath());
				}
				BdgProperties bdgProperties = new BdgProperties();
				String localPath = bdgProperties.osTempDirDossier(tenantInfo.getTenantId())+"/"+bdgProperties.tmpFileName(taEdition.getFichierNom());

				try { 
					Files.write(Paths.get(localPath), taEdition.getFichierBlob());
					
					AnalyseFileReport afr = new AnalyseFileReport();
					afr.initializeBuildDesignReportConfig(localPath);
					afr.ajouteLogo();
					afr.closeDesignReportConfig();
					

					String pdf = taProformaService.generePDF(selectedDocumentDTO.getId(),localPath,taEdition.getMapParametreEdition(),listeResourcesPath,taEdition.getTheme());
					PrimeFaces.current().executeScript("window.open('/edition?fichier="+URLEncoder.encode(pdf, "UTF-8")+"')");
					masterEntity = taProformaService.findById(masterEntity.getIdDocument());
					updateTab();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}


		}
	}
	
	public void imprimeEdition(TaEditionDTO edition, String modeEdition, String pourClient) {
		//ICI COMMENCE LA PARTIE actDialogImprimer
		 Map<String,Object> options = new HashMap<String, Object>();
	       
	        
	        Map<String,List<String>> params = new HashMap<String,List<String>>();
	        List<String> list = new ArrayList<String>();
	        list.add(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION));
	        params.put("modeEcranDefaut", list);
	        
	        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			Map<String, Object> mapParametreEdition = null;
			
	
				
			mapParametreEdition = new HashMap<>();
			boolean editionClient = false;	
			editionClient = LibConversion.StringToBoolean(pourClient,false);
			mapParametreEdition.put("editionClient", editionClient);
			
			List<TaPreferences> liste= taPreferencesService.findByGroupe("proforma");
			mapParametreEdition.put("preferences", liste);
			mapParametreEdition.put("gestionLot", masterEntity.getGestionLot());
			  
			if(!editionClient) {

		    }else {
		        //Mise à jour de la mise à dispostion
		        if(masterEntity.getTaMiseADisposition()==null) {
		            masterEntity.setTaMiseADisposition(new TaMiseADisposition());
		        }
		        Date dateMiseADispositionClient = new Date();
		        masterEntity.getTaMiseADisposition().setImprimePourClient(dateMiseADispositionClient);
		        masterEntity = taProformaService.mergeAndFindById(masterEntity,ITaProformaServiceRemote.validationContext);
		    }
//			if(!editionClient) {
//				switch (modeEdition) {
//				case "BROUILLON":
//					if(masterEntity.getResteAReglerComplet().compareTo(BigDecimal.ZERO)==0) {
//						mapParametreEdition.put("mode", "BROUILLONAPAYER");
//					} else {
//						mapParametreEdition.put("mode", "BROUILLON");
//					}
//					break;
//				case "DUPLICATA":
//					if(masterEntity.getResteAReglerComplet().compareTo(BigDecimal.ZERO)==0) {
//						mapParametreEdition.put("mode", "DUPLICATAPAYER");
//					} else {
//						mapParametreEdition.put("mode", "DUPLICATA");
//					}
//					break;
//				case "PAYER":
//					if(masterEntity.getResteAReglerComplet().compareTo(BigDecimal.ZERO)==0) {
//						mapParametreEdition.put("mode", "PAYER");
//					} else {
//						mapParametreEdition.put("mode", "PAYER");
//					}
//					break;
//	
//				default:
//					break;
//				}
//			} else {
//				if(masterEntity.getResteAReglerComplet().compareTo(BigDecimal.ZERO)==0) {
//					mapParametreEdition.put("mode", "CLIENTAPAYER");
//				} else {
//					mapParametreEdition.put("mode", "CLIENT");
//				}
//			}
			
			
			EditionParam editionParam = new EditionParam();
			editionParam.setIdActionInterne(ConstActionInterne.EDITION_PROFORMA_DEFAUT);
			editionParam.setMapParametreEdition(mapParametreEdition);
			
			sessionMap.put(EditionParam.NOM_OBJET_EN_SESSION, editionParam);
	        
			
			
			//ICI s'arrete la partie actDialogImprimer
			
			//ICI COMMENCE LA PARTIE handleReturnDialogImprimer
			String pdf="";
			if(edition == null) {//si aucune edition n'est choisie
				
				TaActionEdition actionImprimer = new TaActionEdition();
				actionImprimer.setCodeAction(TaActionEdition.code_impression);

				//on appelle une methode qui va aller chercher (et elle crée un fichier xml (writing)) elle même l'edition par defaut choisie en fonction de l'action si il y a 
				pdf = taProformaService.generePDF(selectedDocumentDTO.getId(),mapParametreEdition,null,null,actionImprimer);
				
			}else {//si une edition est choisie
				
				  TaEdition taEdition = null;
				  
				  if( edition.getId() != null) {//si l'édition choisie n'est pas l'edition defaut programme
					  try {
							//on récupère l'objet édition
							taEdition = taEditionService.findById(edition.getId());
						} catch (FinderException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				  }
				
				if(taEdition == null) {
					//si cette edition n'existe pas, on prend celle programme
					taEdition = taEditionService.rechercheEditionDisponibleProgrammeDefaut("", ConstActionInterne.EDITION_PROFORMA_DEFAUT).get(0);
				}
					if(taEdition!=null) {
						taEdition.setMapParametreEdition(mapParametreEdition);
						List<String> listeResourcesPath = null;
						if(taEdition.getResourcesPath()!=null) {
							listeResourcesPath = new ArrayList<>();
							listeResourcesPath.add(taEdition.getResourcesPath());
						}

						try { 
							//on créer le fichier birt xml de cette édition dans /tmp/bdg/demo
							String localPath  = taProformaService.writingFileEdition(taEdition);
							//on genere le pdf avec le fichier xml crée ci-dessus
							 pdf = taProformaService.generePDF(selectedDocumentDTO.getId(),localPath,taEdition.getMapParametreEdition(),listeResourcesPath,taEdition.getTheme());
							

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				
			}
			//Enfin on ouvre un onglet pour afficher le PDF
			try {
				String urlEncoded = URLEncoder.encode(pdf, "UTF-8");
				PrimeFaces.current().executeScript("window.open('/edition?fichier="+urlEncoded+"')");
				masterEntity = taProformaService.findById(masterEntity.getIdDocument());
			} catch (FinderException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			updateTab();
		  
			
			
			//ICI FINI LA PARTIE handleReturnDialogImprimer
	}	
	
	// *******************************************************************************
	//			THEME IMPRESSION
	public void theme1() {
		
		setTheme("defaultTheme");
		setLibrairie("bdgFactTheme1");
				
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
	
		sessionMap.put("Theme", getTheme());
		sessionMap.put("Librairie", getLibrairie());
	}
	
	public void theme2() {
		setTheme("theme1");
		setLibrairie("bdgFactTheme1");
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
	
		sessionMap.put("Theme", getTheme());
		sessionMap.put("Librairie", getLibrairie());
	}
	
	public void theme3() {
		setTheme("theme2");
		setLibrairie("bdgFactTheme1");
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
	
		sessionMap.put("Theme", getTheme());
		sessionMap.put("Librairie", getLibrairie());
	}
	// *******************************************************************************
	
//	public void actImprimerEtiquetteCB(ActionEvent actionEvent) {
//		
//		etiquetteCodeBarreBean.videListe();
//		try {
//			for (TaLProforma l : masterEntity.getLignes()) {
//				etiquetteCodeBarreBean.getListeArticle().add(l.getTaArticle());
//			}
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
//		
//		if(ConstWeb.DEBUG) {
//		   	FacesContext context = FacesContext.getCurrentInstance();  
//		    context.addMessage(null, new FacesMessage("Proforma", "actImprimerEtiquetteCB")); 
//		}
//	}

	public boolean pasDejaOuvert() {
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_DEVIS);
		return tabsCenter.ongletDejaOuvert(b)==null;
	} 

//	public void onRowSimpleSelect(SelectEvent event) {
//
//		try {
//			if(pasDejaOuvert()==false){
//				onRowSelect(event);
//	
////				autoCompleteMapDTOtoUI();
////				
////				masterEntity = taProformaService.findById(selectedDocumentDTO.getId());
////				masterEntity.calculeTvaEtTotaux();
////				
////				valuesLigne = IHMmodel();
////				
////				if(ConstWeb.DEBUG) {
////					FacesContext context = FacesContext.getCurrentInstance();  
////					context.addMessage(null, new FacesMessage("Proforma", 
////							"Chargement du Proforma N°"+selectedDocumentDTO.getCodeTiers()
////							)); 
////				}
//			} else {
//				tabViews.selectionneOngletCentre(LgrTab.TYPE_TAB_DEVIS);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	} 

//	public void updateTab(){
//
//		try {	
//			modeEcranLigne.setMode(EnumModeObjet.C_MO_CONSULTATION);	
//			if(selectedDocumentDTOs!=null && selectedDocumentDTOs.length>0) {
//				selectedDocumentDTO = selectedDocumentDTOs[0];
//			}
//			if(selectedDocumentDTO.getId()!=null && selectedDocumentDTO.getId()!=0) {
//				masterEntity = taProformaService.findById(selectedDocumentDTO.getId());
//			}
//			valuesLigne = IHMmodel();
//			
//			masterEntity.calculeTvaEtTotaux();
//			changementTiers(false);
//			
//			mapperModelToUI.map(masterEntity, selectedDocumentDTO);
//			
//			autoCompleteMapDTOtoUI();
//			
//			if(ConstWeb.DEBUG) {
//				FacesContext context = FacesContext.getCurrentInstance();  
//				context.addMessage(null, new FacesMessage("Proforma", 
//						"Chargement du Proforma N°"+selectedDocumentDTO.getCodeTiers()
//						)); 
//			}
//		
//		} catch (FinderException e) {
//			e.printStackTrace();
//		}
//	}
	
	public void onRowSelect(SelectEvent event) {  
		LgrTab b = new LgrTab();
		b.setTypeOnglet(LgrTab.TYPE_TAB_PROFORMA);
		b.setTitre("Facture proforma");
		b.setTemplate("documents/proforma.xhtml");
		b.setIdTab(LgrTab.ID_TAB_PROFORMA);
		b.setStyle(LgrTab.CSS_CLASS_TAB_PROFORMA);
		tabsCenter.ajouterOnglet(b);
		tabViews.selectionneOngletCentre(b);	
		updateTab();
	
		scrollToTop();
	} 
	
	

//	public boolean editable() {
//		return !modeEcran.dataSetEnModif();
//	}
//
//	public boolean editableEnInsertionUniquement() {
//		return !modeEcran.dataSetEnInsertion();
//	}
	
//	public void actDialogTiers(ActionEvent actionEvent) {
//
//		//		PrimeFaces.current().dialog().openDynamic("aide");
//
//		Map<String,Object> options = new HashMap<String, Object>();
//		options.put("modal", true);
//		options.put("draggable", false);
//		options.put("resizable", false);
//		options.put("contentHeight", 640);
//		options.put("modal", true);
//
//		//Map<String,List<String>> params = null;
//		Map<String,List<String>> params = new HashMap<String,List<String>>();
//		List<String> list = new ArrayList<String>();
//		list.add(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION));
//		params.put("modeEcranDefaut", list);
//
//		PrimeFaces.current().dialog().openDynamic("tiers/dialog_tiers", options, params);
//
//		//		FacesContext context = FacesContext.getCurrentInstance();  
//		//		context.addMessage(null, new FacesMessage("Proforma", "actAbout")); 	    
//	}
//
//	public void handleReturnDialogTiers(SelectEvent event) {
//		if(event!=null && event.getObject()!=null) {
//			taTiers = (TaTiers) event.getObject();
//		}
//	}
//
//	public void actDialogTypeCivilite(ActionEvent actionEvent) {
//
//		//		PrimeFaces.current().dialog().openDynamic("aide");
//
//		Map<String,Object> options = new HashMap<String, Object>();
//		options.put("modal", true);
//		options.put("draggable", false);
//		options.put("resizable", false);
//		options.put("contentHeight", 640);
//		options.put("modal", true);
//
//		//Map<String,List<String>> params = null;
//		Map<String,List<String>> params = new HashMap<String,List<String>>();
//		List<String> list = new ArrayList<String>();
//		list.add(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION));
//		params.put("modeEcranDefaut", list);
//
//		PrimeFaces.current().dialog().openDynamic("tiers/dialog_type_civilite", options, params);
//
//		//		FacesContext context = FacesContext.getCurrentInstance();  
//		//		context.addMessage(null, new FacesMessage("Proforma", "actAbout")); 	    
//	}
//
//	public void handleReturnDialogTypeCivilite(SelectEvent event) {
//		//		if(event!=null && event.getObject()!=null) {
//		//			taTCivilite = (TaTCivilite) event.getObject();
//		//		}
//	}
//
//
//
//	public void actDialogTypeEntite(ActionEvent actionEvent) {
//
//		//		PrimeFaces.current().dialog().openDynamic("aide");
//
//		Map<String,Object> options = new HashMap<String, Object>();
//		options.put("modal", true);
//		options.put("draggable", false);
//		options.put("resizable", false);
//		options.put("contentHeight", 640);
//		options.put("modal", true);
//
//		//Map<String,List<String>> params = null;
//		Map<String,List<String>> params = new HashMap<String,List<String>>();
//		List<String> list = new ArrayList<String>();
//		list.add(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION));
//		params.put("modeEcranDefaut", list);
//
//		PrimeFaces.current().dialog().openDynamic("tiers/dialog_type_entite", options, params);
//
//		//		FacesContext context = FacesContext.getCurrentInstance();  
//		//		context.addMessage(null, new FacesMessage("Proforma", "actAbout")); 	    
//	}
//
//	public void handleReturnDialogTypeEntite(SelectEvent event) {
//		//		if(event!=null && event.getObject()!=null) {
//		//			taTEntite = (TaTEntite) event.getObject();
//		//		}
//	}
//
//	public boolean etatBouton(String bouton) {
//		boolean retour = true;
//		switch (modeEcran.getMode()) {
//		case C_MO_INSERTION:
//			switch (bouton) {
//				case "annuler":
//				case "enregistrer":
//				case "fermer":
//					retour = false;
//					break;
//				default:
//					break;
//			}
//			break;
//		case C_MO_EDITION:
//			switch (bouton) {
//			case "annuler":
//			case "enregistrer":
//			case "fermer":
//				retour = false;
//				break;
//			default:
//				break;
//			}
//			break;
//		case C_MO_CONSULTATION:
//			switch (bouton) {
//			case "inserer":
//			case "fermer":
//				retour = false;
//				break;
//			case "supprimerListe":retour = false;break;	
//			case "supprimer":
//			case "modifier":
//			case "imprimer":
//				if(selectedDocumentDTO!=null && selectedDocumentDTO.getId()!=null  && selectedDocumentDTO.getId()!=0 ) retour = false;
//				break;				
//			default:
//				break;
//		}
//			break;
//		default:
//			break;
//		}
//		
//		return retour;
//
//	}
//
//	public boolean etatBoutonLigne(String bouton) {
//		boolean retour = true;
//		if(modeEcran.dataSetEnModif()) {
//			retour = false;
//		}
//		switch (modeEcranLigne.getMode()) {
//		case C_MO_INSERTION:
//			switch (bouton) {
//			case "annuler":
//			case "enregistrer":
//			case "fermer":
//				retour = false;
//				break;
//			case "rowEditor":
//				retour = modeEcran.dataSetEnModif()?true:false;
//				break;
//			default:
//				break;
//			}
//			break;
//		case C_MO_EDITION:
//			switch (bouton) {
//			case "annuler":
//			case "enregistrer":
//			case "fermer":
//				retour = false;
//				break;
//			case "rowEditor":
//				retour = modeEcran.dataSetEnModif()?true:false;
//				break;
//			default:
//				break;
//			}
//			break;
//		case C_MO_CONSULTATION:
//			switch (bouton) {
//			case "supprimer":
//			case "modifier":
//			case "inserer":
//			case "imprimer":
//			case "fermer":
//				retour = false;
//				break;
//			case "rowEditor":
//				retour = modeEcran.dataSetEnModif()?true:false;
//				break;
//			default:
//				break;
//			}
//			break;
//		default:
//			break;
//		}
//
//		return retour;
//
//	}
	
	public void validateLignesDocument(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		String msg = "";

		try {
			//String selectedRadio = (String) value;

			String nomChamp =  (String) component.getAttributes().get("nomChamp");

			//msg = "Mon message d'erreur pour : "+nomChamp;

			validateUIField(nomChamp,value);
			TaLProformaDTO dtoTemp =new TaLProformaDTO();
			PropertyUtils.setSimpleProperty(dtoTemp, nomChamp, value);
			taLProformaService.validateDTOProperty(dtoTemp, nomChamp, ITaLProformaServiceRemote.validationContext );

			//selectedDocumentDTO.setAdresse1Adresse("abcd");

			//		  if(selectedRadio.equals("aa")) {
			//			  throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
			//		  }

		} catch(Exception e) {
			msg += e.getMessage();
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}
	
	public void validateDocument(FacesContext context, UIComponent component, Object value) throws ValidatorException {
	
		String messageComplet = "";
		try {
			String nomChamp =  (String) component.getAttributes().get("nomChamp");
			
			if(nomChamp.equals(Const.C_TX_REM_HT_DOCUMENT)||nomChamp.equals(Const.C_TX_REM_TTC_DOCUMENT)){
				if(value==null || value.equals(""))value=BigDecimal.ZERO;
			}
			//validation automatique via la JSR bean validation
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Set<ConstraintViolation<TaProformaDTO>> violations = factory.getValidator().validateValue(TaProformaDTO.class,nomChamp,value);
			if (violations.size() > 0) {
				messageComplet = "Erreur de validation : ";
				for (ConstraintViolation<TaProformaDTO> cv : violations) {
					messageComplet+=" "+cv.getMessage()+"\n";
				}
				throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, messageComplet, messageComplet));
			} else {
				//aucune erreur de validation "automatique", on déclanche les validations du controller
				validateUIField(nomChamp,value);
			}
		} catch(Exception e) {
			//messageComplet += e.getMessage();
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, messageComplet, messageComplet));
		}
	}

	public void validateDocumentAvantEnregistrement( Object value) throws ValidatorException {

		String msg = "";

		try {
			//taBonReceptionService.validateDTOProperty(selectedDocumentDTO,Const.C_CODE_TIERS,  ITaProformaServiceRemote.validationContext );

		} catch(Exception e) {
			msg += e.getMessage();
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}
	
	public void validateLigneDocumentAvantEnregistrement( Object value) throws ValidatorException {

		String msg = "";

		try {
			if(selectedLigneJSF.getTaCoefficientUnite()!=null) {
				if(!calculCoherenceAffectationCoefficientQte2(selectedLigneJSF.getDto().getQte2LDocument())) {
					setLigneAReenregistrer(selectedLigneJSF);
					PrimeFaces.current().executeScript("PF('wVdialogQte2Proforma').show()");
				}

			}
		} catch(Exception e) {
			msg += e.getMessage();
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}
	
//	public void autcompleteSelection(SelectEvent event) {
//		Object value = event.getObject();
//		//		FacesMessage msg = new FacesMessage("Selected", "Item:" + value);
//
//		String nomChamp =  (String) event.getComponent().getAttributes().get("nomChamp");
//		System.out.println("ProformaController.autcompleteSelection() : "+nomChamp);
//
//		if(value!=null) {
//		if(value instanceof String && value.equals(Const.C_AUCUN))value="";	
//		if(value instanceof TaUniteDTO && ((TaUniteDTO) value).getCodeUnite()!=null && ((TaUniteDTO) value).getCodeUnite().equals(Const.C_AUCUN))value=null;	
//		}
//		validateUIField(nomChamp,value);
//	}

	public boolean validateUIField(String nomChamp,Object value) {

		boolean changement=false;

		try {				
			if(nomChamp.equals(Const.C_CODE_TIERS)) {
				TaTiers entity = null;
				if(value!=null){
					if(value instanceof TaTiersDTO){
//						entity=(TaTiers) value;
						entity = taTiersService.findByCode(((TaTiersDTO)value).getCodeTiers());
					}else{
						entity = taTiersService.findByCode((String)value);
					}
					
					changement=!entity.equalsCode(masterEntity.getTaTiers());


				}
				masterEntity.setTaTiers(entity);
				if(changement){
					String nomTiers=masterEntity.getTaTiers().getNomTiers();
					((TaProformaDTO)selectedDocumentDTO).setLibelleDocument("Proforma N°"+selectedDocumentDTO.getCodeDocument()+" - "+nomTiers);										
					if(!disableTtc() && !disableTtcSiDocSansTVA()){
						masterEntity.setTtc(entity.getTtcTiers());
						((TaProformaDTO)selectedDocumentDTO).setTtc(LibConversion.intToBoolean(masterEntity.getTtc()));
					}									
					changementTiers(true);
				}
			}else if(nomChamp.equals(Const.C_NUM_LOT)) {
				
			}else if(nomChamp.equals(Const.C_DATE_DOCUMENT)) {
				dateDansPeriode((Date)value,nomChamp);

			}else if(nomChamp.equals(Const.C_DATE_ECH_DOCUMENT)) {				
				dateDansPeriode((Date)value,nomChamp);
			}else if(nomChamp.equals(Const.C_CODE_ARTICLE)) {
				TaArticle entity = null;
				TaPrix prix =null;
				boolean changementArticleLigne = false;
				if(value!=null){
					if(value instanceof TaArticleDTO){
						entity = taArticleService.findByCode(((TaArticleDTO) value).getCodeArticle());
					}else{
						entity = taArticleService.findByCode((String)value);
					}
				}
				if(entity!=null) {
					 prix = entity.chercheTarif(masterEntity.getTaTiers());
					 if(prix==null)prix=new TaPrix(BigDecimal.ZERO,BigDecimal.ZERO);
				}
				if(selectedLigneJSF.getTaArticle()== null || selectedLigneJSF.getTaArticle().getIdArticle()!=entity.getIdArticle()) {
					changementArticleLigne = true;
				}
				selectedLigneJSF.setTaArticle(entity);
				masterEntityLigne.setTaArticle(entity);
				if(changementArticleLigne) {
					if(entity!=null)selectedLigneJSF.getDto().setLibLDocument(entity.getLibellecArticle());
//					selectedLigneJSF.getDto().setDluo(LibDate.incrementDate(selectedLigneJSF.getDateDocument(), LibConversion.stringToInteger(entity.getParamDluo(), 0)+1  , 0, 0));
//					selectedLigneJSF.getDto().setLibelleLot(entity.getLibellecArticle());
				}
				selectedLigneJSF.setTaUnite1(null);
				selectedLigneJSF.setTaUnite2(null);
				selectedLigneJSF.getDto().setU2LDocument(null);
				selectedLigneJSF.getDto().setQte2LDocument(BigDecimal.ZERO);
				TaRapportUnite rapport = null;
				if(entity!=null) rapport=entity.getTaRapportUnite();
				TaCoefficientUnite coef = null;
				if(rapport!=null){
					if(rapport.getTaUnite2()!=null) {
						coef=recupCoefficientUnite(rapport.getTaUnite1().getCodeUnite(),rapport.getTaUnite2().getCodeUnite());
						selectedLigneJSF.setTaCoefficientUnite(coef);
					}
				}
				
				if(coef!=null){
					selectedLigneJSF.setTaCoefficientUnite(coef);
					if(entity.getTaUnite1()!=null) {
						selectedLigneJSF.getDto().setU1LDocument(entity.getTaUnite1().getCodeUnite());
						selectedLigneJSF.setTaUnite1(taUniteService.findByCode(entity.getTaUnite1().getCodeUnite()));
					}
					if(coef.getUniteB()!=null && coef.getUniteB().getCodeUnite().equals(rapport.getTaUnite2().getCodeUnite())){
						selectedLigneJSF.getDto().setU2LDocument(coef.getUniteB().getCodeUnite());
						selectedLigneJSF.setTaUnite2(taUniteService.findByCode(coef.getUniteB().getCodeUnite()));
					}else if(coef.getUniteA()!=null && coef.getUniteA().getCodeUnite().equals(rapport.getTaUnite2().getCodeUnite())){
						selectedLigneJSF.getDto().setU2LDocument(coef.getUniteA().getCodeUnite());
						selectedLigneJSF.setTaUnite2(taUniteService.findByCode(coef.getUniteA().getCodeUnite()));
					}
				}else
					if(entity!=null ){
						if(entity.getTaUnite1()!=null) {
							selectedLigneJSF.getDto().setU1LDocument(entity.getTaUnite1().getCodeUnite());
							selectedLigneJSF.setTaUnite1(taUniteService.findByCode(entity.getTaUnite1().getCodeUnite()));
						}
						for (TaRapportUnite obj : entity.getTaRapportUnites()) {
							if(obj.getTaUnite1()!=null && obj.getTaUnite1().getCodeUnite().equals(selectedLigneJSF.getDto().getU1LDocument())){
								if(obj!=null){
									if(obj.getTaUnite2()!=null) {										
										coef=recupCoefficientUnite(obj.getTaUnite1().getCodeUnite(),obj.getTaUnite2().getCodeUnite());
									}
								}
								selectedLigneJSF.setTaCoefficientUnite(coef);
								if(coef!=null) {
									if(coef.getUniteB()!=null && coef.getUniteB().getCodeUnite().equals(obj.getTaUnite2().getCodeUnite())){
									selectedLigneJSF.getDto().setU2LDocument(coef.getUniteB().getCodeUnite());
									selectedLigneJSF.setTaUnite2(taUniteService.findByCode(coef.getUniteB().getCodeUnite()));
									}else if(coef.getUniteA()!=null && coef.getUniteA().getCodeUnite().equals(obj.getTaUnite2().getCodeUnite())){
										selectedLigneJSF.getDto().setU2LDocument(coef.getUniteA().getCodeUnite());
										selectedLigneJSF.setTaUnite2(taUniteService.findByCode(coef.getUniteA().getCodeUnite()));
									}
								}else if(obj.getTaUnite2()!=null){
									selectedLigneJSF.getDto().setU2LDocument(obj.getTaUnite2().getCodeUnite());
									selectedLigneJSF.setTaUnite2(taUniteService.findByCode(obj.getTaUnite2().getCodeUnite()));
								}
							}							
						}
					}
				if(entity!=null && entity.getTaTva()!=null && masterEntity.isGestionTVA()){
					selectedLigneJSF.getDto().setCodeTvaLDocument(entity.getTaTva().getCodeTva());
					selectedLigneJSF.getDto().setTauxTvaLDocument(entity.getTaTva().getTauxTva());
				}else {
					selectedLigneJSF.getDto().setCodeTvaLDocument(null);
					selectedLigneJSF.getDto().setTauxTvaLDocument(null);
					
				}
				if(prix!=null){
					selectedLigneJSF.getDto().setQteLDocument(new BigDecimal(1));
					validateUIField(Const.C_QTE_L_DOCUMENT, selectedLigneJSF.getDto().getQteLDocument());
					if(masterEntity.getTtc()==1) {
						selectedLigneJSF.getDto().setPrixULDocument(prix.getPrixttcPrix());
					} else {
						selectedLigneJSF.getDto().setPrixULDocument(prix.getPrixPrix());
					}

					selectedLigneJSF.getDto().setMtHtLDocument(masterEntityLigne.getMtHtLDocument());
					selectedLigneJSF.getDto().setMtTtcLDocument(masterEntityLigne.getMtTtcLDocument());
				}
				
				if(entity!=null && entity.getTaRTaTitreTransport()!=null) {
					BigDecimal qteCrd = BigDecimal.ZERO;	
					selectedLigneJSF.setTaTitreTransport(entity.getTaRTaTitreTransport().getTaTitreTransport());
						if(entity.getTaRTaTitreTransport().getQteTitreTransport()!=null )
							qteCrd=entity.getTaRTaTitreTransport().getQteTitreTransport();
						selectedLigneJSF.getDto().setQteTitreTransport(selectedLigneJSF.getDto().getQteLDocument().multiply(qteCrd));
				} else {
					selectedLigneJSF.setTaTitreTransport(null);
					selectedLigneJSF.getDto().setQteTitreTransport(BigDecimal.ZERO);
				}


				masterEntityLigne.setTauxTvaLDocument(selectedLigneJSF.getDto().getTauxTvaLDocument());
				masterEntityLigne.setPrixULDocument(selectedLigneJSF.getDto().getPrixULDocument());
				masterEntityLigne.calculMontant();
				selectedLigneJSF.getDto().setMtHtLDocument(masterEntityLigne.getMtHtLDocument());
				selectedLigneJSF.getDto().setMtTtcLDocument(masterEntityLigne.getMtTtcLDocument());
			}else if(nomChamp.equals(Const.C_PRIX_U_L_DOCUMENT)) {
				BigDecimal prix=BigDecimal.ZERO;
				if(value!=null){
					if(!value.equals("")){
						prix=(BigDecimal)value;
					}}
					masterEntityLigne.setPrixULDocument(prix);
					masterEntityLigne.calculMontant();
					selectedLigneJSF.getDto().setMtHtLDocument(masterEntityLigne.getMtHtLDocument());
					selectedLigneJSF.getDto().setMtTtcLDocument(masterEntityLigne.getMtTtcLDocument());
				
		   } else if(nomChamp.equals(Const.C_QTE_L_DOCUMENT)) {
				BigDecimal qte=BigDecimal.ZERO;
				if(value!=null){
					if(!value.equals("")){
						qte=(BigDecimal)value;
					}
					selectedLigneJSF.setTaCoefficientUnite(recupCoefficientUnite(selectedLigneJSF.getDto().getU1LDocument(),selectedLigneJSF.getDto().getU2LDocument()));
					if(selectedLigneJSF.getTaCoefficientUnite()!=null) {
						if(selectedLigneJSF.getTaCoefficientUnite().getOperateurDivise()) 
							selectedLigneJSF.getDto().setQte2LDocument((qte).divide(selectedLigneJSF.getTaCoefficientUnite().getCoeff()
									,MathContext.DECIMAL128).setScale(selectedLigneJSF.getTaCoefficientUnite().getNbDecimale(),BigDecimal.ROUND_HALF_UP));
						else selectedLigneJSF.getDto().setQte2LDocument((qte).multiply(selectedLigneJSF.getTaCoefficientUnite().getCoeff()));
					}else  {
						selectedLigneJSF.getDto().setQte2LDocument(BigDecimal.ZERO);
						masterEntityLigne.setQte2LDocument(null);
					}
				} else {
					masterEntityLigne.setQte2LDocument(null);
				}
				masterEntityLigne.setQteLDocument(qte);
				selectedLigneJSF.getDto().setMtHtLDocument(masterEntityLigne.getMtHtLDocument());
				selectedLigneJSF.getDto().setMtTtcLDocument(masterEntityLigne.getMtTtcLDocument());
			
		} else if(nomChamp.equals(Const.C_U1_L_DOCUMENT)) {
			TaUnite entity =null;
			if(value!=null){
				if(value instanceof TaUnite){
					entity=(TaUnite) value;
					entity = taUniteService.findByCode(entity.getCodeUnite());
				}else{
					entity = taUniteService.findByCode((String)value);
				}
				masterEntityLigne.setU1LDocument(entity.getCodeUnite());
				selectedLigneJSF.getDto().setU1LDocument(entity.getCodeUnite());
			}else {
				selectedLigneJSF.getDto().setU1LDocument("");
				masterEntityLigne.setU1LDocument(null);
			}
			selectedLigneJSF.setTaCoefficientUnite(recupCoefficientUnite(selectedLigneJSF.getDto().getU1LDocument(),selectedLigneJSF.getDto().getU2LDocument()));
			validateUIField(Const.C_QTE_L_DOCUMENT, selectedLigneJSF.getDto().getQteLDocument());	
		} else if(nomChamp.equals(Const.C_QTE2_L_DOCUMENT)) {
			BigDecimal qte=BigDecimal.ZERO;
			if(value==null) {
				masterEntityLigne.setQte2LDocument(null);
			}else if(!value.equals("")){
				qte=(BigDecimal)value;
			}
			selectedLigneJSF.getDto().setQte2LDocument(qte);
		}else if(nomChamp.equals(Const.C_U2_L_DOCUMENT)) {
			TaUnite entity =null;
			if(value!=null){
				if(value instanceof TaUnite){
					entity=(TaUnite) value;
					entity = taUniteService.findByCode(entity.getCodeUnite());
				}else{
					entity = taUniteService.findByCode((String)value);
				}
				masterEntityLigne.setU2LDocument(entity.getCodeUnite());
				selectedLigneJSF.getDto().setU2LDocument(entity.getCodeUnite());
			}else {
				selectedLigneJSF.getDto().setU2LDocument("");
				masterEntityLigne.setU2LDocument(null);
			}
			selectedLigneJSF.setTaCoefficientUnite(recupCoefficientUnite(selectedLigneJSF.getDto().getU1LDocument(),selectedLigneJSF.getDto().getU2LDocument()));
			validateUIField(Const.C_QTE_L_DOCUMENT, selectedLigneJSF.getDto().getQteLDocument());
		} else if(nomChamp.equals(Const.C_CODE_TITRE_TRANSPORT)) {
			TaTitreTransport entity = null;
			if(value!=null){
				entity = (TaTitreTransport) value;
			}
			if(entity!=null) {
				masterEntityLigne.setCodeTitreTransport(entity.getCodeTitreTransport());
				selectedLigneJSF.setTaTitreTransport(entity);
				selectedLigneJSF.setTaTitreTransport(entity);
			}else {
				masterEntityLigne.setCodeTitreTransport(null);
				masterEntityLigne.setQteTitreTransport(null);
				selectedLigneJSF.setTaTitreTransport(entity);
			}			
		} else if(nomChamp.equals(Const.C_CODE_T_PAIEMENT)) {
			TaTPaiement entity = null;
			if(value!=null){
				if(value instanceof TaTPaiement){
					entity = (TaTPaiement) value;
				}else{
					entity = taTPaiementService.findByCode((String)value);
				}
			}
			if(entity!=null && masterEntity.getTaTPaiement()!=null){
				if(entity.getCodeTPaiement()!=null) {
					changement=!entity.getCodeTPaiement().equals(masterEntity.getTaTPaiement().getCodeTPaiement());
				}
			}
			masterEntity.setTaTPaiement(entity);
			taTPaiement=entity;
//			if(changement || masterEntity.getTaReglement()==null) {
//				actInitReglement();
//			}
		}else if(nomChamp.equals(Const.C_CODE_C_PAIEMENT)) {
			TaCPaiement entity = null;
			if(value!=null){
				entity = (TaCPaiement) value;
			}
			setTaCPaiementDoc(entity);
			if(entity!=null) {
				selectedCPaiement.setCodeCPaiement(entity.getCodeCPaiement());
				selectedCPaiement.setFinMoisCPaiement(entity.getFinMoisCPaiement());
				selectedCPaiement.setReportCPaiement(entity.getReportCPaiement());
				selectedCPaiement.setLibCPaiement(entity.getLibCPaiement());
			}			
		}  else	if(nomChamp.equals(Const.C_TX_REM_HT_DOCUMENT)) {
			BigDecimal tx=BigDecimal.ZERO;
			if(value!=null){
				if(!value.equals("")){
					tx=(BigDecimal)value;
				}
				masterEntity.setTxRemHtDocument(tx);
				mapperModelToUI.map(masterEntity, selectedDocumentDTO);
			}
		} else	if(nomChamp.equals(Const.C_TX_REM_TTC_DOCUMENT)) {
			BigDecimal tx=BigDecimal.ZERO;
			if(value!=null){
				if(!value.equals("")){
					tx=(BigDecimal)value;
				}
				masterEntity.setTxRemTtcDocument(tx);
				mapperModelToUI.map(masterEntity, selectedDocumentDTO);
			}
		} else	if(nomChamp.equals(Const.C_REM_TX_L_DOCUMENT)) {
			BigDecimal tx=BigDecimal.ZERO;
			if(value!=null){
				if(!value.equals("")){
					tx=(BigDecimal)value;
				}
			}
			masterEntityLigne.setRemTxLDocument(tx);
			selectedLigneJSF.getDto().setMtHtLDocument(masterEntityLigne.getMtHtLDocument());
			selectedLigneJSF.getDto().setMtTtcLDocument(masterEntityLigne.getMtTtcLDocument());
		}else	if(nomChamp.equals(Const.C_EXPORT)) {
		} else if(nomChamp.equals(Const.C_CODE_T_TVA_DOC)) {
			if(value!=null){
				if(value instanceof TaTTvaDoc){
					selectedDocumentDTO.setCodeTTvaDoc(((TaTTvaDoc) value).getCodeTTvaDoc());
				}else if(value instanceof String){
					selectedDocumentDTO.setCodeTTvaDoc((String) value);
				}
			}
			initLocalisationTVA();
			//si TTC est vrai et que codeTvaDoc différent de France alors on remets TTC à faux
			//car la saisie dans ce cas là doit se faire sur le HT
			if(selectedDocumentDTO.getTtc() && disableTtcSiDocSansTVA()) {
				selectedDocumentDTO.setTtc(false);
				validateUIField(Const.C_TTC, selectedDocumentDTO.getTtc());
			}
		} else if(nomChamp.equals(Const.C_TTC)) {
			masterEntity.setTtc(LibConversion.booleanToInt(selectedDocumentDTO.getTtc()));

		}	
		return false;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

//	public List<TaTiers> tiersAutoComplete(String query) {
//		List<TaTiers> allValues = taTiersService.selectAll();
//		List<TaTiers> filteredValues = new ArrayList<TaTiers>();
//
//		for (int i = 0; i < allValues.size(); i++) {
//			TaTiers civ = allValues.get(i);
//			if(query.equals("*") || civ.getCodeTiers().toLowerCase().contains(query.toLowerCase())) {
//				filteredValues.add(civ);
//			}
//		}
//
//		return filteredValues;
//	}
//
//	public List<TaTiersDTO> tiersAutoCompleteDTOLight(String query) {
//		List<TaTiersDTO> allValues = taTiersService.findByCodeLight("*");
//		List<TaTiersDTO> filteredValues = new ArrayList<TaTiersDTO>();
//
//		for (int i = 0; i < allValues.size(); i++) {
//			TaTiersDTO civ = allValues.get(i);
//			if(query.equals("*") || civ.getCodeTiers().toLowerCase().contains(query.toLowerCase())
//					|| civ.getNomTiers().toLowerCase().contains(query.toLowerCase())) {
//				filteredValues.add(civ);
//			}
//		}
//
//		return filteredValues;
//	}
//	
//	public List<TaArticle> articleAutoComplete(String query) {
//		List<TaArticle> allValues = taArticleService.selectAll();
//		List<TaArticle> filteredValues = new ArrayList<TaArticle>();
//
//		for (int i = 0; i < allValues.size(); i++) {
//			TaArticle civ = allValues.get(i);
//			if(query.equals("*") || civ.getCodeArticle().toLowerCase().contains(query.toLowerCase())) {
//				filteredValues.add(civ);
//			}
//		}
//
//		return filteredValues;
//	}
//	public List<TaArticleDTO> articleAutoCompleteDTOLight(String query) {
//		List<TaArticleDTO> allValues = taArticleService.findByCodeLight("*");
//		List<TaArticleDTO> filteredValues = new ArrayList<TaArticleDTO>();
//
//		for (int i = 0; i < allValues.size(); i++) {
//			TaArticleDTO civ = allValues.get(i);
//			if(query.equals("*") || civ.getCodeArticle().toLowerCase().contains(query.toLowerCase())) {
//				filteredValues.add(civ);
//			}
//		}
//
//		return filteredValues;
//	}
//	
//	public List<TaTPaiement> typePaiementAutoComplete(String query) {
//		List<TaTPaiement> allValues = taTPaiementService.selectAll();
//		List<TaTPaiement> filteredValues = new ArrayList<TaTPaiement>();
//		TaTPaiement cp = new TaTPaiement();
//		cp.setCodeTPaiement(Const.C_AUCUN);
//		filteredValues.add(cp);
//		for (int i = 0; i < allValues.size(); i++) {
//			cp = allValues.get(i);
//			if(query.equals("*") || cp.getCodeTPaiement().toLowerCase().contains(query.toLowerCase())) {
//				filteredValues.add(cp);
//			}
//		}
//
//		return filteredValues;
//	}
//	
//	public List<TaCPaiement> typeCPaiementAutoComplete(String query) {
//		List<TaCPaiement> allValues = taCPaiementService.selectAll();
//		List<TaCPaiement> filteredValues = new ArrayList<TaCPaiement>();
//		TaCPaiement cp = new TaCPaiement();
//		cp.setCodeCPaiement(Const.C_AUCUN);
//		filteredValues.add(cp);
//		for (int i = 0; i < allValues.size(); i++) {
//			cp = allValues.get(i);
//			if(query.equals("*") || cp.getCodeCPaiement().toLowerCase().contains(query.toLowerCase())) {
//				filteredValues.add(cp);
//			}
//		}
//
//		return filteredValues;
//	}
//	
//	public List<TaTTvaDoc> typeTvaDocAutoComplete(String query) {
//		List<TaTTvaDoc> allValues = taTTvaDocService.selectAll();
//		List<TaTTvaDoc> filteredValues = new ArrayList<TaTTvaDoc>();
//		TaTTvaDoc cp = new TaTTvaDoc();
//		cp.setCodeTTvaDoc(Const.C_AUCUN);
//		filteredValues.add(cp);
//		for (int i = 0; i < allValues.size(); i++) {
//			cp = allValues.get(i);
//			if(query.equals("*") || cp.getCodeTTvaDoc().toLowerCase().contains(query.toLowerCase())) {
//				filteredValues.add(cp);
//			}
//		}
//
//		return filteredValues;
//	}
//	
//	public List<TaUnite> uniteAutoComplete(String query) {
//		List<TaUnite> allValues = taUniteService.selectAll();
//		List<TaUnite> filteredValues = new ArrayList<TaUnite>();
//		TaUnite civ=new TaUnite();
//		civ.setCodeUnite(Const.C_AUCUN);
//		filteredValues.add(civ);
//		for (int i = 0; i < allValues.size(); i++) {
//			 civ = allValues.get(i);
//			if(query.equals("*") || civ.getCodeUnite().toLowerCase().contains(query.toLowerCase())) {
//				filteredValues.add(civ);
//			}
//		}
//
//		return filteredValues;
//	}
//
//	public List<TaEntrepot> entrepotAutoComplete(String query) {
//		List<TaEntrepot> allValues = taEntrepotService.selectAll();
//		List<TaEntrepot> filteredValues = new ArrayList<TaEntrepot>();
//
//		for (int i = 0; i < allValues.size(); i++) {
//			TaEntrepot civ = allValues.get(i);
//			if(query.equals("*") || civ.getCodeEntrepot().toLowerCase().contains(query.toLowerCase())) {
//				filteredValues.add(civ);
//			}
//		}
//
//		return filteredValues;
//	}
//
//	public List<String> emplacementAutoComplete(String query) {
//		List<String> filteredValues = new ArrayList<String>();
//		if(selectedLigneJSF!=null && selectedLigneJSF.getTaEntrepot()!=null) {
//			List<String> allValues = taEtatStockService.emplacementEntrepot(selectedLigneJSF.getTaEntrepot().getCodeEntrepot(),null);
//			
//			for (int i = 0; i < allValues.size(); i++) {
//				String civ = allValues.get(i);
//				if(civ !=null && (query.equals("*") || civ.toLowerCase().contains(query.toLowerCase()))) {
//					filteredValues.add(civ);
//				}
//			}
//		}
//		return filteredValues;
//	}
//	
//	public void actDialogControleLot(ActionEvent actionEvent) {
//		
////		PrimeFaces.current().dialog().openDynamic("aide");
//    
//        Map<String,Object> options = new HashMap<String, Object>();
//        options.put("modal", true);
//        options.put("draggable", false);
//        options.put("resizable", false);
//        options.put("contentHeight", 640);
//        options.put("modal", true);
//        
//        //Map<String,List<String>> params = null;
//        Map<String,List<String>> params = new HashMap<String,List<String>>();
//        List<String> list = new ArrayList<String>();
//        list.add(modeEcran.modeString(EnumModeObjet.C_MO_INSERTION));
//        params.put("modeEcranDefaut", list);
//        
//        PrimeFaces.current().dialog().openDynamic("solstyce/dialog_controle_article", options, params);
//		
////		FacesContext context = FacesContext.getCurrentInstance();  
////		context.addMessage(null, new FacesMessage("Tiers", "actAbout")); 	    
//	}
//	
//	public void handleReturnDialogControleLot(SelectEvent event) {
//		if(event!=null && event.getObject()!=null) {
//			//taTTiers = (TaTTiers) event.getObject();
//		}
//	}


//	public void setInsertAuto(boolean insertAuto) {
////		String oncomplete="jQuery('.myclass .ui-datatable-data tr').last().find('span.ui-icon-pencil').each(function(){jQuery(this).click()});";
////		if(insertAuto) {
////			rc.setOncomplete(oncomplete);
////			System.out.println("Ajoutera une nouvelle ligne automatiquement");
////		} else {
////			rc.setOncomplete(null);
////			System.out.println("N'ajoutera pas de nouvelle ligne automatiquement");
////		}
//		this.insertAuto = insertAuto;
//	}

	public void initialisationDesInfosComplementaires(Boolean recharger,String typeARecharger){
		/*
		 * verifier que le type d'adresse existe
		 * verifier que le tiers à des adresses de ce tpe
		 * remplir les maps et changer les clauses where des DAO de modeles
		 * 
		 * remplir les modèles 
		 * avoir dans l'ordre :
		 * - adresse de l'infos facture si elle existe
		 * - adresse du type demandé s'il y en a
		 * - le reste des adresses du tiers
		 */
		
		try {
			if(((TaProformaDTO)selectedDocumentDTO).getCodeDocument()!=null) {
				taInfosDocument = taInfosDocumentService.findByCodeProforma(((TaProformaDTO)selectedDocumentDTO).getCodeDocument());
			} else {
				taInfosDocument = new TaInfosProforma();
			}
			
			if(taInfosDocument!=null && taInfosDocument.getNomTiers()!=null)selectedDocumentDTO.setNomTiers(taInfosDocument.getNomTiers());
			if(taInfosDocument!=null && taInfosDocument.getCodeTTvaDoc()!=null)selectedDocumentDTO.setCodeTTvaDoc(taInfosDocument.getCodeTTvaDoc());
			if(recharger) {				
				if(masterEntity.getTaTiers()!=null){
					masterEntity.setTaTiers(taTiersService.findById(masterEntity.getTaTiers().getIdTiers()));
					selectedDocumentDTO.setCodeTiers(masterEntity.getTaTiers().getCodeTiers());
					selectedDocumentDTO.setNomTiers(masterEntity.getTaTiers().getNomTiers());
					selectedDocumentDTO.setPrenomTiers(masterEntity.getTaTiers().getPrenomTiers());
					selectedDocumentDTO.setNomEntreprise("");
					if(masterEntity.getTaTiers().getTaEntreprise()!=null)
						selectedDocumentDTO.setNomEntreprise(masterEntity.getTaTiers().getTaEntreprise().getNomEntreprise());
					if (masterEntity.getTaTiers().getTaTTvaDoc()!=null){
						selectedDocumentDTO.setCodeTTvaDoc(masterEntity.getTaTiers().getTaTTvaDoc().getCodeTTvaDoc());
					}
				}
			}
	
			if(typeARecharger==Const.RECHARGE_ADR_FACT||typeARecharger=="")
				initialisationDesAdrFact(recharger);
			if(typeARecharger==Const.RECHARGE_ADR_LIV||typeARecharger=="")
				initialisationDesAdrLiv(recharger);
			if(typeARecharger==Const.RECHARGE_C_PAIEMENT||typeARecharger=="")
				initialisationDesCPaiement(recharger);
			if(typeARecharger==Const.RECHARGE_INFOS_TIERS||typeARecharger=="")
				initialisationDesInfosTiers(recharger);
		}  catch (FinderException e) {
			e.printStackTrace();
		}
	}
	
//	public void initialisationDesAdrFact(Boolean recharger){
//		try {
//
//			LinkedList<AdresseInfosFacturationDTO> liste = new LinkedList<AdresseInfosFacturationDTO>();
//	
//			boolean leTiersADesAdresseFact = false;
//			if(masterEntity.getTaTiers()!=null){
//				if(typeAdresseFacturation!=null && taTAdrService.findByCode(typeAdresseFacturation)!=null) { //le type d'adresse existe bien
//					leTiersADesAdresseFact = masterEntity.getTaTiers().aDesAdressesDuType(typeAdresseFacturation); //le tiers a des adresse de ce type
//				}	
//			
//			if(leTiersADesAdresseFact) { 
//				//ajout des adresse de facturation au modele
//				for (TaAdresse taAdresse : masterEntity.getTaTiers().getTaAdresses()) {
//					if(taAdresse.getTaTAdr()!=null && taAdresse.getTaTAdr().getCodeTAdr().equals(typeAdresseFacturation)){
//						liste.add(mapperModelToUIAdresseInfosDocument.map(taAdresse, classModelAdresseFact));
//					}
//				}
//			}else{
//
//			}
//			
//			//ajout des autres types d'adresse
//			for (TaAdresse taAdresse : masterEntity.getTaTiers().getTaAdresses()) {
//				liste.add(mapperModelToUIAdresseInfosDocument.map(taAdresse, classModelAdresseFact));
//			}
//			if(liste.isEmpty()) 
//				liste.add(mapperModelToUIAdresseInfosDocument.map(new TaAdresse(), classModelAdresseFact));			
//			}
//			//ajout de l'adresse de livraison inscrite dans l'infos facture
//			if(taInfosDocument!=null) {
//				if(recharger )
//					liste.add(mapperModelToUIInfosDocVersAdresseFact.map(taInfosDocument, classModelAdresseFact));
//				else
//					liste.addFirst(mapperModelToUIInfosDocVersAdresseFact.map(taInfosDocument, classModelAdresseFact));
//			}
//			
//			if (!liste.isEmpty())
//				selectedAdresseFact.setIHMAdresse(liste.getFirst());
//			else
//				selectedAdresseFact.setIHMAdresse(new AdresseInfosFacturationDTO());
//			
//			System.out.println("ProformaController.initialisationDesAdrFact() ** "+selectedAdresseFact.getAdresse1Adresse());
//		
//		}catch (FinderException e) {
//			e.printStackTrace();
//		}
//				
//	}
//
//
//	public void initialisationDesAdrLiv(Boolean recharger){
//		try {
//		
//			LinkedList<AdresseInfosLivraisonDTO> liste = new LinkedList<AdresseInfosLivraisonDTO>();
//
//		boolean leTiersADesAdresseLiv = false;
//		if(masterEntity.getTaTiers()!=null){
//			if(typeAdresseLivraison!=null && taTAdrService.findByCode(typeAdresseLivraison)!=null) { //le type d'adresse existe bien
//				leTiersADesAdresseLiv = masterEntity.getTaTiers().aDesAdressesDuType(typeAdresseLivraison); //le tiers a des adresse de ce type
//			}
//		
//		
//		if(leTiersADesAdresseLiv) { 
//			//ajout des adresse de livraison au modele
//			for (TaAdresse taAdresse : masterEntity.getTaTiers().getTaAdresses()) {
//				if(taAdresse.getTaTAdr()!=null && taAdresse.getTaTAdr().getCodeTAdr().equals(typeAdresseLivraison)){
//					liste.add(mapperModelToUIAdresseLivInfosDocument.map(taAdresse, classModelAdresseLiv));
//				}
//			}
//		}
//		//ajout des autres types d'adresse
//		for (TaAdresse taAdresse : masterEntity.getTaTiers().getTaAdresses()) {
//			if(leTiersADesAdresseLiv && taAdresse.getTaTAdr()!=null && !taAdresse.getTaTAdr().getCodeTAdr().equals(typeAdresseLivraison)){
//				liste.add(mapperModelToUIAdresseLivInfosDocument.map(taAdresse, classModelAdresseLiv));
//			} else {
//				liste.add(mapperModelToUIAdresseLivInfosDocument.map(taAdresse, classModelAdresseLiv));
//			}
//		}
//		}
//		//ajout de l'adresse de livraison inscrite dans l'infos facture
//		if(taInfosDocument!=null) {
//			
//			if(recharger )
//				liste.add(mapperModelToUIInfosDocVersAdresseLiv.map(taInfosDocument, classModelAdresseLiv));
//			else
//				liste.addFirst(mapperModelToUIInfosDocVersAdresseLiv.map(taInfosDocument, classModelAdresseLiv));
//		}		
//		if (!liste.isEmpty())
//			((AdresseInfosLivraisonDTO) selectedAdresseLiv).setIHMAdresse(liste.getFirst());
//		else
//			((AdresseInfosLivraisonDTO) selectedAdresseLiv).setIHMAdresse(new AdresseInfosLivraisonDTO());
//		
//		}catch (FinderException e) {
//			e.printStackTrace();
//		}
//				
//	}
	

	
	public void initialisationDesCPaiement(Boolean recharger) {
		LinkedList<TaCPaiementDTO> liste = new LinkedList<TaCPaiementDTO>();
		try {
		selectedCPaiement = new TaCPaiementDTO();
		TaInfosProforma taInfosDocument = null;
		if (masterEntity != null) {
			if (masterEntity.getCodeDocument()!=null&& masterEntity.getCodeDocument() != "")
				taInfosDocument = taInfosDocumentService
						.findByCodeProforma(masterEntity.getCodeDocument());
			else
				taInfosDocument = new TaInfosProforma();

			
			taCPaiementDoc = null;
			List<TaCPaiement> listeCPaiement=taCPaiementService.rechercheParType(TaProforma.TYPE_DOC);
			if(listeCPaiement!=null && !listeCPaiement.isEmpty())taCPaiementDoc=listeCPaiement.get(0);
			

			if (taTCPaiementService.findByCode(TaTCPaiement.C_CODE_TYPE_PROFORMA) != null
					&& taTCPaiementService.findByCode(
							TaTCPaiement.C_CODE_TYPE_PROFORMA).getTaCPaiement() != null) {
				taCPaiementDoc = taTCPaiementService.findByCode(
						TaTCPaiement.C_CODE_TYPE_PROFORMA).getTaCPaiement();
			}
			int report = 0;
			int finDeMois = 0;


				if (masterEntity.getTaTiers() != null) {
					if (masterEntity.getTaTiers().getTaTPaiement() != null) {
						if (masterEntity.getTaTiers().getTaTPaiement()
								.getReportTPaiement() != null)
							report = masterEntity.getTaTiers().getTaTPaiement()
									.getReportTPaiement();
						if (masterEntity.getTaTiers().getTaTPaiement()
								.getFinMoisTPaiement() != null)
							finDeMois = masterEntity.getTaTiers()
									.getTaTPaiement().getFinMoisTPaiement();
					}

					if (masterEntity.getTaTiers().getTaCPaiement() == null
							|| (report != 0 || finDeMois != 0)) {
						if (taCPaiementDoc == null
								|| (report != 0 || finDeMois != 0)) {// alors on
																		// met
																		// au
																		// moins
																		// une
																		// condition
																		// vide
							TaCPaiementDTO ihm = new TaCPaiementDTO();
							ihm.setReportCPaiement(report);
							ihm.setFinMoisCPaiement(finDeMois);
							liste.add(ihm);
						}
					} else
						liste.add(
								mapperModelToUICPaiementInfosDocument.map(
										masterEntity.getTaTiers()
												.getTaCPaiement(),
										classModelCPaiement));
				}
			

			if (taCPaiementDoc != null  )
				liste.add(
						mapperModelToUICPaiementInfosDocument.map(taCPaiementDoc,
								classModelCPaiement));
			masterEntity.setTaInfosDocument(taInfosDocument);
			// ajout de l'adresse de livraison inscrite dans l'infos facture
			if (taInfosDocument != null) {
				if (recharger)
					liste.add(
							mapperModelToUIInfosDocVersCPaiement.map(
									masterEntity.getTaInfosDocument(),
									classModelCPaiement));
				else
					liste.addFirst(
							mapperModelToUIInfosDocVersCPaiement.map(
									masterEntity.getTaInfosDocument(),
									classModelCPaiement));
			}
		}
		if (!liste.isEmpty()) {
			((TaCPaiementDTO) selectedCPaiement)
					.setSWTCPaiement(liste.getFirst());
		} else {
			((TaCPaiementDTO) selectedCPaiement)
					.setSWTCPaiement(new TaCPaiementDTO());
		}

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void calculDateEcheance() {
		calculDateEcheance(false);
	}
	
	public void calculDateEcheance(Boolean appliquer) {
		if(selectedDocumentDTO!=null){
		if(((TaProformaDTO)selectedDocumentDTO).getId()==0|| appliquer) {

			Integer report = null;
			Integer finDeMois = null;
			if(((TaCPaiementDTO)selectedCPaiement)!=null /*&& ((TaCPaiementDTO)selectedCPaiement).getCodeCPaiement()!=null*/) { 
				if(((TaCPaiementDTO)selectedCPaiement).getReportCPaiement()!=null)
					report = ((TaCPaiementDTO)selectedCPaiement).getReportCPaiement();
				if(((TaCPaiementDTO)selectedCPaiement).getFinMoisCPaiement()!=null)
					finDeMois = ((TaCPaiementDTO)selectedCPaiement).getFinMoisCPaiement();
			}
			masterEntity.setDateDocument(selectedDocumentDTO.getDateDocument());
			((TaProformaDTO)selectedDocumentDTO).setDateEchDocument(taProformaService.calculDateEcheance(masterEntity,report, finDeMois));
		}
		}
	}
	
	
//	public void initialisationDesInfosComplementaires(){
//		initialisationDesInfosComplementaires(false,"");
//	}
//
//	public void actReinitAdrFact() throws Exception {
//		initialisationDesInfosComplementaires(true,Const.RECHARGE_ADR_FACT);
//		actModifier();
//	}
//	
//	public void actReinitAdrLiv() throws Exception {
//		initialisationDesInfosComplementaires(true,Const.RECHARGE_ADR_LIV);
//		actModifier();
//	}
//	
//	public void actReinitCPaiement() throws Exception {
//		initialisationDesInfosComplementaires(true,Const.RECHARGE_C_PAIEMENT);
//		calculDateEcheance(true);
//		actModifier();
//	}
//	
//	public void actAppliquerCPaiement() throws Exception {
//		calculDateEcheance(true);
//		actModifier();
//	}
//	
//	public void actReinitInfosTiers() throws Exception {
//		initialisationDesInfosComplementaires(true,Const.RECHARGE_INFOS_TIERS);
//		actModifier();
//	}
//	
//	public void modifMode(EnumModeObjet mode){
//		if (!VerrouInterface.isVerrouille() ){
//			try {
//				if(!modeEcran.dataSetEnModif()) {
//					if(mode.equals(EnumModeObjet.C_MO_EDITION)) {
//						actModifier();
//					} 
//					if(mode.equals(EnumModeObjet.C_MO_INSERTION)) {
//						actInserer(null);
//					} 					
//				}
//			} catch (Exception e1) {
//				e1.printStackTrace();  
//			}
//		}
//	}
//	
//	public boolean disableTtc(){
//		if(masterEntity!=null && masterEntity.getLignes()!=null)
//			return masterEntity.getLignes().size()>0;
//		return false;
//	}

	@Override
	public void calculTypePaiement(boolean recharger) {
		// TODO Auto-generated method stub
		
	}

@Override
public void IHMmodel(TaLProformaDTOJSF dtoJSF, TaLProforma ligne) {
	LgrDozerMapper<TaLProforma,TaLProformaDTO> mapper = new LgrDozerMapper<TaLProforma,TaLProformaDTO>();
	TaLProformaDTO dto = dtoJSF.getDto();

		dto = (TaLProformaDTO) mapper.map(ligne, TaLProformaDTO.class);
		dtoJSF.setDto(dto);
//		dtoJSF.setTaLot(ligne.getTaLot());
//		if(dtoJSF.getArticleLotEntrepotDTO()==null) {
//			dtoJSF.setArticleLotEntrepotDTO(new ArticleLotEntrepotDTO());
//		}
//		if(ligne.getTaLot()!=null) {
//			dtoJSF.getArticleLotEntrepotDTO().setNumLot(ligne.getTaLot().getNumLot());
//		}
		dtoJSF.setTaArticle(ligne.getTaArticle());
		if(ligne.getTaArticle()!=null){
			dtoJSF.setTaRapport(ligne.getTaArticle().getTaRapportUnite());
		}
//		dtoJSF.setTaEntrepot(ligne.getTaEntrepot());
		if(ligne.getU1LDocument()!=null) {
			dtoJSF.setTaUnite1(new TaUnite());
			dtoJSF.getTaUnite1().setCodeUnite(ligne.getU1LDocument());
		}
		if(ligne.getU2LDocument()!=null) {
			dtoJSF.setTaUnite2(new TaUnite());
			dtoJSF.getTaUnite2().setCodeUnite(ligne.getU2LDocument());
		}
		if(ligne.getCodeTitreTransport()!=null) {
			dtoJSF.setTaTitreTransport(new TaTitreTransport());
			dtoJSF.getTaTitreTransport().setCodeTitreTransport(ligne.getCodeTitreTransport());
		}

		dtoJSF.updateDTO(false);
	
}

@Override
public void actAppliquerCPaiement() throws Exception {
	calculDateEcheance(true);
	actModifier();
}



public void onClearArticle(AjaxBehaviorEvent event){
	super.onClearArticle(event);
	selectedLigneJSF.setTaLot(null);
	selectedLigneJSF.getDto().setNumLot(null);
	if(selectedLigneJSF.getArticleLotEntrepotDTO()!=null)selectedLigneJSF.getArticleLotEntrepotDTO().setNumLot(null);
	
	masterEntityLigne.calculMontant();
	masterEntity.calculeTvaEtTotaux();
	validateUIField(Const.C_CODE_ARTICLE, null);
}

public String getTheme() {
	return theme;
}

public void setTheme(String theme) {
	this.theme = theme;
}

public String getLibrairie() {
	return librairie;
}

public void setLibrairie(String librairie) {
	this.librairie = librairie;
}



public void actInsererLigne(ActionEvent actionEvent) {
	super.actInsererLigne(actionEvent);

	TaEtat etat = taProformaService.etatLigneInsertion(masterEntity);
	masterEntityLigne.addREtatLigneDoc(etat);
	
	selectedLigneJSF.setTaREtatLigneDocument(masterEntityLigne.getTaREtatLigneDocument());
	if(etat!=null)selectedLigneJSF.getDto().setCodeEtat(etat.getCodeEtat());
//	selectedLigneJSF.getDto().setDatePrevue(selectedDocumentDTO.getDateLivDocument());
}


public void updateTab(){

	try {	
		super.updateTab();
		autoriseSuppressionLigne(true);
		selectedEtatDocument=null;
		if(masterEntity!=null && masterEntity.getTaREtat()!=null)
			selectedEtatDocument=masterEntity.getTaREtat().getTaEtat();
		
		selectedDocumentDTO.setRegleDocument(masterEntity.getRegleCompletDocument());
		selectedDocumentDTO.setResteAReglerComplet(masterEntity.getResteAReglerComplet());
	
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


public boolean autoriseSuppressionLigne() {
	return autoriseSuppressionLigne(false,selectedLigneJSF);
}

public void actSupprimerLigne(ActionEvent actionEvent) {
	try {
		if(autoriseSuppressionLigne()) {
			if(autorisationBean.autoriseMenu(IModulesProgramme.ID_MODULE_ABONNEMENT)) {
				  if(selectedLigneJSF.getDto().getIdLDocument() != null) {
//						//List<TaLEcheance> liste  = taLEcheanceService.findAllByIdLFacture(selectedLigneJSF.getDto().getIdLDocument());
//						List<TaLigneALigneEcheance> liste  = taLigneALigneEcheanceService.findAllByIdLDocumentAndTypeDoc(selectedLigneJSF.getDto().getIdLDocument(), TaProforma.TYPE_DOC);
//						if(liste != null && !liste.isEmpty()) {
//							for (TaLigneALigneEcheance li : liste) {
//								if(!listeLigneALigneEcheanceASupprimer.contains(li)) {
//									listeLigneALigneEcheanceASupprimer.add(li);
//								}
//								
//							}
//
//						}
				  }
				}
			super.actSupprimerLigne(actionEvent);
		}
	} catch(Exception e) {
		e.printStackTrace();
	}
}


public void onRowEdit(RowEditEvent event) {
	try {
		if(autoriseSuppressionLigne(false, selectedLigneJSF)){
			super.onRowEdit(event);
		}

	} catch (Exception e) {
		e.printStackTrace();
		FacesContext context = FacesContext.getCurrentInstance();  
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enregistrement document", e.getMessage()));	
		context.validationFailed();
	}
}


public RetourAutorisationLiaison autoriseSuppression(boolean silencieux) {
	return autoriseSuppressionLigne(silencieux);
}
public RetourAutorisationLiaison autoriseSuppression() {
	return autoriseSuppressionLigne(false);
}


public boolean documentLie(boolean silencieux) {
	docLie=rechercheSiDocLie();
	return docLie!=null && docLie.size()>0;
}

public boolean ligneDocumentLie(boolean silencieux) {
	ligneLie=rechercheSiLigneDocLie(selectedLigneJSF);
	return ligneLie!=null && ligneLie.size()>0;
}


public void onClearTaTitreTransport(AjaxBehaviorEvent event){
	selectedLigneJSF.getDto().setCodeTitreTransport(null);
	selectedLigneJSF.getDto().setQteTitreTransport(null);
	selectedLigneJSF.setTaTitreTransport(null);
	masterEntityLigne.setCodeTitreTransport(null);
	masterEntityLigne.setQteTitreTransport(null);
}

}
