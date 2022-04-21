package fr.legrain.articles.ecran;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ejb.FinderException;
import javax.naming.NamingException;
import javax.persistence.EntityManager;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ViewersObservables;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import fr.legrain.article.dto.TaTNoteArticleDTO;
import fr.legrain.article.model.TaTNoteArticle;
import fr.legrain.articles.ArticlesPlugin;
import fr.legrain.articles.editor.EditorInputTypeNoteArticle;
import fr.legrain.articles.editor.EditorTypeNoteArticle;
import fr.legrain.bdg.article.service.remote.ITaTNoteArticleServiceRemote;
import fr.legrain.bdg.model.mapping.LgrDozerMapper;
import fr.legrain.gestCom.Appli.Const;
//import fr.legrain.gestCom.Appli.LgrDozerMapper;
import fr.legrain.gestCom.Appli.ModelGeneralObjetEJB;
import fr.legrain.gestCom.librairiesEcran.editor.EditorAide;
import fr.legrain.gestCom.librairiesEcran.editor.EditorInputAide;
import fr.legrain.gestCom.librairiesEcran.swt.EJBBaseControllerSWTStandard;
import fr.legrain.gestCom.librairiesEcran.swt.LgrShellUtil;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideRechercheSWT;
import fr.legrain.gestCom.librairiesEcran.swt.ParamAfficheAideSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideControllerSWT;
import fr.legrain.gestCom.librairiesEcran.swt.SWTPaAideRechercheControllerSWT;
import fr.legrain.gestCom.librairiesEcran.workbench.ChangementDeSelectionEvent;
import fr.legrain.gestCom.librairiesEcran.workbench.EJBLgrEditorPart;
import fr.legrain.gestCom.librairiesEcran.workbench.LgrPartListener;
import fr.legrain.lib.data.EnumModeObjet;
import fr.legrain.lib.data.ExceptLgr;
import fr.legrain.lib.data.InfosVerifSaisie;
import fr.legrain.lib.data.VerrouInterface;
import fr.legrain.lib.ejb.EJBLookup;
import fr.legrain.lib.gui.ParamAffiche;
import fr.legrain.lib.gui.ResultAffiche;
import fr.legrain.lib.gui.RetourEcranEvent;
import fr.legrain.lib.gui.RetourEcranListener;
import fr.legrain.lib.gui.aide.PaAideRechercheSWT;
import fr.legrain.lib.gui.aide.PaAideSWT;
import fr.legrain.lib.gui.aide.ParamAfficheAide;
import fr.legrain.lib.gui.aide.ResultAide;
import fr.legrain.lib.gui.grille.LgrTableViewer;
import fr.legrain.lib.gui.grille.LgrViewerSupport;
import fr.legrain.lib.validator.AbstractApplicationDAOClient;
import fr.legrain.libMessageLGR.LgrMess;
import fr.legrain.tiers.clientutility.JNDILookupClass;

public class SWTPaTypeNoteArticleController extends EJBBaseControllerSWTStandard
		implements RetourEcranListener {

	static Logger logger = Logger.getLogger(SWTPaTypeNoteArticleController.class.getName());
	private PaTypeNoteArticleSWT vue = null;
	private ITaTNoteArticleServiceRemote dao = null;//new TaTNoteDAO();

	private Object ecranAppelant = null;
	private TaTNoteArticleDTO swtTypeNote;
	private TaTNoteArticleDTO swtOldTypeNote;
	private Realm realm;
	private DataBindingContext dbc;

	private LgrTableViewer tableViewer;
	private WritableList writableList;
	private IObservableValue selectedTypeNote;
	private String[] listeChamp;
	private String nomClassController = this.getClass().getSimpleName();
	private MapChangeListener changeListener = new MapChangeListener();
	private Class classModel = TaTNoteArticleDTO.class;
	private ModelGeneralObjetEJB<TaTNoteArticle,TaTNoteArticleDTO> modelTypeNote = null;//new ModelGeneralObjet<SWTTNote,TaTNoteDAO,TaTNote>(dao,classModel);
	
	private LgrDozerMapper<TaTNoteArticleDTO,TaTNoteArticle> mapperUIToModel  = new LgrDozerMapper<TaTNoteArticleDTO,TaTNoteArticle>();
	private TaTNoteArticle taTNote = null;
	
	
	public SWTPaTypeNoteArticleController(PaTypeNoteArticleSWT vue) {
		this(vue,null);
	}

	public SWTPaTypeNoteArticleController(PaTypeNoteArticleSWT vue,EntityManager em) {
//		if(em!=null) {
//			setEm(em);
//		}
		try {
			dao = new EJBLookup<ITaTNoteArticleServiceRemote>().doLookup(JNDILookupClass.getInitialContext(),EJBLookup.EJB_NAME_TA_T_NOTE_ARTICLE_SERVICE, ITaTNoteArticleServiceRemote.class.getName());
		} catch (NamingException e1) {
			logger.error("",e1);
		}
		modelTypeNote = new ModelGeneralObjetEJB<TaTNoteArticle,TaTNoteArticleDTO>(dao);
		setVue(vue);

		// a faire avant l'initialisation du Binding
		vue.getGrille().addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				setSwtOldTypeNote();
			}
		});
		vue.getShell().addShellListener(this);

		// Branchement action annuler : empeche la fermeture automatique de la
		// fenetre sur ESC
		vue.getShell().addTraverseListener(new Traverse());

		initController();
	}

	private void initController() {
		try {
			setGrille(vue.getGrille());
			initSashFormWeight();
			setDaoStandard(dao);
			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);
			
			initMapComposantChamps();
			initVerifySaisie();
			initMapComposantDecoratedField();
			listeComponentFocusableSWT(listeComposantFocusable);
			initFocusOrder();
			initActions();
			initDeplacementSaisie(listeComposantFocusable);

			branchementBouton();

			Menu popupMenuFormulaire = new Menu(vue.getShell(), SWT.POP_UP);
			Menu popupMenuGrille = new Menu(vue.getShell(), SWT.POP_UP);
			Menu[] tabPopups = new Menu[] { popupMenuFormulaire,popupMenuGrille };
			this.initPopupAndButtons(mapActions, tabPopups);
			vue.getPaCorpsFormulaire().setMenu(popupMenuFormulaire);
			vue.getPaGrille().setMenu(popupMenuGrille);

			initEtatBouton();
		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("Erreur : PaTiersController", e);
		}
	}

	/**
	 * Creation des objet pour l'interface, a partir de l'entite principale.<br>
	 * @return
	 */
	
	public void bind(PaTypeNoteArticleSWT PaTypeNoteSWT) {
		try {
			realm = SWTObservables.getRealm(vue.getParent().getDisplay());

			tableViewer = new LgrTableViewer(PaTypeNoteSWT.getGrille());
			tableViewer.createTableCol(classModel,PaTypeNoteSWT.getGrille(), nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE,0);
			listeChamp = tableViewer.setListeChampGrille(nomClassController,
					Const.C_FICHIER_LISTE_CHAMP_GRILLE);

//			ObservableListContentProvider viewerContent = new ObservableListContentProvider();
//			tableViewer.setContentProvider(viewerContent);
//
//			IObservableMap[] attributeMaps = BeansObservables.observeMaps(
//					viewerContent.getKnownElements(), classModel,listeChamp);
//
//			tableViewer.setLabelProvider(new ObservableMapLabelProvider(attributeMaps));
//			writableList = new WritableList(realm, modelTypeWeb.remplirListe(), classModel);
//			tableViewer.setInput(writableList);
			
			// Set up data binding.
			LgrViewerSupport.bind(tableViewer, 
					new WritableList(modelTypeNote.remplirListe(), classModel),
					BeanProperties.values(listeChamp)
					);

			selectedTypeNote = ViewersObservables.observeSingleSelection(tableViewer);

			dbc = new DataBindingContext(realm);

			dbc.getValidationStatusMap().addMapChangeListener(changeListener);
			tableViewer.selectionGrille(0);

			setTableViewerStandard(tableViewer);
			setDbcStandard(dbc);

			bindingFormMaitreDetail(dbc, realm, selectedTypeNote,classModel);
			changementDeSelection();
			selectedTypeNote.addChangeListener(new IChangeListener() {

				public void handleChange(ChangeEvent event) {
					changementDeSelection();
				}

			});

		} catch (Exception e) {
			vue.getLaMessage().setText(e.getMessage());
			logger.error("", e);
		}
	}
	
	private void changementDeSelection() {
		if(selectedTypeNote!=null && selectedTypeNote.getValue()!=null) {
			if(((TaTNoteArticleDTO) selectedTypeNote.getValue()).getId()!=null) {
				try {
					taTNote = dao.findById(((TaTNoteArticleDTO) selectedTypeNote.getValue()).getId());
				} catch (FinderException e) {
					logger.error("", e);
				}
			}
			//null a tester ailleurs, car peut etre null en cas d'insertion
			fireChangementDeSelection(new ChangementDeSelectionEvent(SWTPaTypeNoteArticleController.this));
		}
	}

	public Composite getVue() {
		return vue;
	}

	public ResultAffiche configPanel(ParamAffiche param) {
		if (param != null) {
			//ibTaTable.ouvreDataset();
			if (((ParamAfficheTypeNoteArticle) param).getFocusDefautSWT() != null && !((ParamAfficheTypeNoteArticle) param).getFocusDefautSWT().isDisposed())
				((ParamAfficheTypeNoteArticle) param).getFocusDefautSWT().setFocus();
			else
				((ParamAfficheTypeNoteArticle) param).setFocusDefautSWT(vue.getGrille());
			vue.getLaTitreFormulaire().setText(((ParamAfficheTypeNoteArticle) param).getTitreFormulaire());
			vue.getLaTitreGrille().setText(((ParamAfficheTypeNoteArticle) param).getTitreGrille());
			vue.getLaTitreFenetre().setText(((ParamAfficheTypeNoteArticle) param).getSousTitre());

			if (param.getEcranAppelant() != null) {
				ecranAppelant = param.getEcranAppelant();
			}
			Map<String,String[]> map = dao.getParamWhereSQL();
			if(param.getIdFiche()!=null &&  !param.getIdFiche().equals("")) {
				if(map==null) map = new HashMap<String,String[]>();
				map.clear();
				map.put("idTNoteArticle",new String[]{"=",param.getIdFiche()});
				dao.setParamWhereSQL(map);
				vue.getBtnTous().setVisible(true);
				vue.getGrille().setVisible(false);
				vue.getLaTitreGrille().setVisible(false);
				vue.getCompositeForm().setWeights(new int[]{0,100});					
			}
			bind(vue);
			// permet de se positionner sur le 1er enregistrement et de remplir
			// le formulaire
			tableViewer.selectionGrille(0);
			tableViewer.tri(classModel, nomClassController,Const.C_FICHIER_LISTE_CHAMP_GRILLE);
			VerrouInterface.setVerrouille(false);
			setSwtOldTypeNote();

			if (param.getModeEcran() != null
					&& param.getModeEcran().compareTo(EnumModeObjet.C_MO_INSERTION) == 0) {
				try {
					actInserer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			}
		}
		return null;
	}
	
	protected void initVerifySaisie() {
		if (mapInfosVerifSaisie == null)
			mapInfosVerifSaisie = new HashMap<Control, InfosVerifSaisie>();
		
		mapInfosVerifSaisie.put(vue.getTfCODE_T_NOTE_ARTICLE(), new InfosVerifSaisie(new TaTNoteArticle(),Const.C_CODE_T_NOTE_ARTICLE,null));
		mapInfosVerifSaisie.put(vue.getTfLIBELLE_T_NOTE_ARTICLE(), new InfosVerifSaisie(new TaTNoteArticle(),Const.C_LIBL_T_NOTE_ARTICLE,null));

		initVerifyListener(mapInfosVerifSaisie, dao);
	}

	protected void initComposantsVue() throws ExceptLgr {
	}

	protected void initMapComposantChamps() {
		if (mapComposantChamps == null)
			mapComposantChamps = new LinkedHashMap<Control, String>();

		if (listeComposantFocusable == null)
			listeComposantFocusable = new ArrayList<Control>();
		listeComposantFocusable.add(vue.getGrille());

		vue.getTfCODE_T_NOTE_ARTICLE().setToolTipText(Const.C_CODE_T_NOTE_ARTICLE);
		vue.getTfLIBELLE_T_NOTE_ARTICLE().setToolTipText(Const.C_LIBL_T_NOTE_ARTICLE);


		mapComposantChamps.put(vue.getTfCODE_T_NOTE_ARTICLE(), Const.C_CODE_T_NOTE_ARTICLE);
		mapComposantChamps.put(vue.getTfLIBELLE_T_NOTE_ARTICLE(), Const.C_LIBL_T_NOTE_ARTICLE);

		for (Control c : mapComposantChamps.keySet()) {
			listeComposantFocusable.add(c);
		}

		listeComposantFocusable.add(vue.getBtnEnregistrer());
		listeComposantFocusable.add(vue.getBtnInserer());
		listeComposantFocusable.add(vue.getBtnModifier());
		listeComposantFocusable.add(vue.getBtnSupprimer());
		listeComposantFocusable.add(vue.getBtnFermer());
		listeComposantFocusable.add(vue.getBtnAnnuler());
		listeComposantFocusable.add(vue.getBtnImprimer());
		listeComposantFocusable.add(vue.getBtnTous());

		if (mapInitFocus == null)
			mapInitFocus = new LinkedHashMap<EnumModeObjet, Control>();
		mapInitFocus.put(EnumModeObjet.C_MO_INSERTION, vue
				.getTfCODE_T_NOTE_ARTICLE());
		mapInitFocus.put(EnumModeObjet.C_MO_EDITION, vue
				.getTfCODE_T_NOTE_ARTICLE());
		mapInitFocus.put(EnumModeObjet.C_MO_CONSULTATION, vue
				.getGrille());

		activeModifytListener();
	}

	protected void initActions() {
		mapCommand = new HashMap<String, IHandler>();
		
		mapCommand.put(C_COMMAND_GLOBAL_MODIFIER_ID, handlerModifier);
		mapCommand.put(C_COMMAND_GLOBAL_ANNULER_ID, handlerAnnuler);
		mapCommand.put(C_COMMAND_GLOBAL_ENREGISTRER_ID, handlerEnregistrer);
		mapCommand.put(C_COMMAND_GLOBAL_INSERER_ID, handlerInserer);
		mapCommand.put(C_COMMAND_GLOBAL_SUPPRIMER_ID, handlerSupprimer);
		mapCommand.put(C_COMMAND_GLOBAL_REFRESH_ID, handlerRefresh);
		mapCommand.put(C_COMMAND_GLOBAL_AIDE_ID, handlerAide);
		mapCommand.put(C_COMMAND_GLOBAL_FERMER_ID, handlerFermer);
		mapCommand.put(C_COMMAND_GLOBAL_IMPRIMER_ID, handlerImprimer);
		mapCommand.put(C_COMMAND_DOCUMENT_AFFICHER_TOUS_ID, handlerAfficherTous);
		
		initFocusCommand(String.valueOf(this.hashCode()),listeComposantFocusable,mapCommand);
		
		if (mapActions == null)
			mapActions = new LinkedHashMap<Button, Object>();
		
		mapActions.put(vue.getBtnAnnuler(), C_COMMAND_GLOBAL_ANNULER_ID);
		mapActions.put(vue.getBtnEnregistrer(), C_COMMAND_GLOBAL_ENREGISTRER_ID);
		mapActions.put(vue.getBtnInserer(), C_COMMAND_GLOBAL_INSERER_ID);
		mapActions.put(vue.getBtnModifier(), C_COMMAND_GLOBAL_MODIFIER_ID);
		mapActions.put(vue.getBtnSupprimer(), C_COMMAND_GLOBAL_SUPPRIMER_ID);
		mapActions.put(vue.getBtnFermer(), C_COMMAND_GLOBAL_FERMER_ID);
		mapActions.put(vue.getBtnImprimer(), C_COMMAND_GLOBAL_IMPRIMER_ID);
		mapActions.put(vue.getBtnTous(), C_COMMAND_DOCUMENT_AFFICHER_TOUS_ID);

		Object[] tabActionsAutres = new Object[] { C_COMMAND_GLOBAL_AIDE_ID, C_COMMAND_GLOBAL_REFRESH_ID };
		mapActions.put(null, tabActionsAutres);
	}

	public SWTPaTypeNoteArticleController getThis() {
		return this;
	}

	@Override
	public boolean onClose() throws ExceptLgr {
		boolean retour = true;
		VerrouInterface.setVerrouille(true);
		switch (getModeEcran().getMode()) {
		case C_MO_INSERTION:
		case C_MO_EDITION:
			if (MessageDialog.openQuestion(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("TypeNoteArticle.Message.Enregistrer"))) {

				try {
					actEnregistrer();
				} catch (Exception e) {
					vue.getLaMessage().setText(e.getMessage());
					logger.error("", e);
				}
			} else
			break;
		case C_MO_CONSULTATION:
			break;
		default:
			break;
		}

		if (retour) {
			if (ecranAppelant instanceof SWTPaAideControllerSWT) {
				setListeEntity(getModelTypeNote().remplirListe());
				dao.initValeurIdTable(taTNote);
				fireRetourEcran(new RetourEcranEvent(this, new ResultAide(
						dao.getChampIdEntite(), dao.getValeurIdTable(),
						selectedTypeNote.getValue())));

				retour = true;
			}
		}
		return retour;
	}

	public void retourEcran(final RetourEcranEvent evt) {
		if (evt.getRetour() != null
				&& (evt.getSource() instanceof SWTPaAideControllerSWT)) {
			if (getFocusAvantAideSWT() instanceof Text) {
				try {
					((Text) getFocusAvantAideSWT()).setText(((ResultAffiche) evt.getRetour()).getResult());					
					ctrlUnChampsSWT(getFocusAvantAideSWT());
				} catch (Exception e) {
					logger.error("",e);
					vue.getLaMessage().setText(e.getMessage());
				}
			}
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getGrille()) {
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						tableViewer.setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}
		} else if (evt.getRetour() != null) {
			if (getFocusAvantAideSWT() instanceof Table) {
				if (getFocusAvantAideSWT() == vue.getGrille()) {
					if(((ResultAffiche) evt.getRetour()).getSelection()!=null)
						tableViewer.setSelection(((ResultAffiche) evt.getRetour()).getSelection(),true);
				}
			}
		}
		super.retourEcran(evt);
	}

	@Override
	protected void actInserer() throws Exception {
		try {
			if(getModeEcran().getMode().compareTo(EnumModeObjet.C_MO_CONSULTATION)==0) {
				VerrouInterface.setVerrouille(true);
				setSwtOldTypeNote();
				swtTypeNote = new TaTNoteArticleDTO();
				taTNote = new TaTNoteArticle();
//				dao.inserer(taTNote);
				modelTypeNote.getListeObjet().add(swtTypeNote);
				writableList = new WritableList(realm, modelTypeNote.getListeObjet(), classModel);
				tableViewer.setInput(writableList);
				tableViewer.refresh();
				tableViewer.setSelection(new StructuredSelection(swtTypeNote));
				getModeEcran().setMode(EnumModeObjet.C_MO_INSERTION);//ejb
				initEtatBouton();
			}

		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		} finally {
			initEtatComposant();
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actModifier() throws Exception {
		try {
			if(!LgrMess.isDOSSIER_EN_RESEAU()){
				setSwtOldTypeNote();
				taTNote = dao.findById(((TaTNoteArticleDTO) selectedTypeNote.getValue()).getId());
			}else{
				if(!setSwtOldTypeNoteRefresh())throw new Exception();
			}			
			dao.modifier(taTNote);
			
			modeEcran.setMode(EnumModeObjet.C_MO_EDITION);//ejb
			initEtatBouton();
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		}
	}
	public boolean containsEntity(TaTNoteArticle entite){
		if(modelTypeNote.getListeEntity()!=null){
			for (Object e : modelTypeNote.getListeEntity()) {
				if(((TaTNoteArticle)e).getIdTNoteArticle()==
					entite.getIdTNoteArticle())return true;
			}
		}
		return false;
	}


	public boolean setSwtOldTypeNoteRefresh() {
		try {	
			if (selectedTypeNote.getValue()!=null){
				TaTNoteArticle taTNoteOld =dao.findById(taTNote.getIdTNoteArticle());
//				taTNoteOld=dao.refresh(taTNoteOld);
				if(containsEntity(taTNote)) 
					modelTypeNote.getListeEntity().remove(taTNote);
				if(!taTNote.getVersionObj().equals(taTNoteOld.getVersionObj())){
					taTNote=taTNoteOld;
					if(!containsEntity(taTNote)) 
						modelTypeNote.getListeEntity().add(taTNote);					
					actRefresh();
					dao.messageNonAutoriseModification();
				}
				taTNote=taTNoteOld;
				if(!containsEntity(taTNote)) 
					modelTypeNote.getListeEntity().add(taTNote);
				changementDeSelection();
				this.swtOldTypeNote=TaTNoteArticleDTO.copy((TaTNoteArticleDTO)selectedTypeNote.getValue());
			}
			return true;
		} catch (Exception e) {
			return false;
		}		
	}

	@Override
	protected void actSupprimer() throws Exception {
//		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			VerrouInterface.setVerrouille(true);
			if(isUtilise())MessageDialog.openInformation(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("Message.suppression"));
			else		
			if (MessageDialog.openConfirm(vue.getShell(), MessagesEcran
					.getString("Message.Attention"), MessagesEcran
					.getString("TypeNoteArticle.Message.Supprimer"))) {

//			dao.begin(transaction);
				TaTNoteArticle u = dao.findById(((TaTNoteArticleDTO) selectedTypeNote.getValue()).getId());
				dao.supprimer(u);				
//			dao.commit(transaction);
			Object suivant=tableViewer.getElementAt(tableViewer.getTable().getSelectionIndex()+1);
			modelTypeNote.removeEntity(taTNote);
			taTNote=null;

//			dao.getModeObjet().setMode(EnumModeObjet.C_MO_CONSULTATION);
			modeEcran.setMode(EnumModeObjet.C_MO_CONSULTATION);
			if(suivant!=null)tableViewer.setSelection(new StructuredSelection(suivant),true);
			else tableViewer.selectionGrille(0);
			actRefresh(); //ajouter pour tester jpa

			}
		} catch (Exception e1) {
			vue.getLaMessage().setText(e1.getMessage());
			logger.error("Erreur : actionPerformed", e1);
		} finally {
//			if(transaction!=null && transaction.isActive()) {
//				transaction.rollback();
//			}
			initEtatBouton();
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actFermer() throws Exception {
		// (controles de sortie et fermeture de la fenetre) => onClose()
		if(onClose())
		closeWorkbenchPart();
	}

	@Override
	protected void actAnnuler() throws Exception {
		// // verifier si l'objet est en modification ou en consultation
		// // si modification ou insertion, alors demander si annulation des
		// // modifications si ok, alors annulation si pas ok, alors arreter le
		// processus d'annulation
		// // si consultation declencher l'action "fermer".
		try {
			VerrouInterface.setVerrouille(true);
			switch (getModeEcran().getMode()) {
			case C_MO_INSERTION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("TypeNoteArticle.Message.Annuler")))) {
					remetTousLesChampsApresAnnulationSWT(dbc);
					if(((TaTNoteArticleDTO) selectedTypeNote.getValue()).getId()==null){
					modelTypeNote.getListeObjet().remove(((TaTNoteArticleDTO) selectedTypeNote.getValue()));
					writableList = new WritableList(realm, modelTypeNote.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.selectionGrille(0);
					}
					dao.annuler(taTNote);
					hideDecoratedFields();
				}
				getModeEcran().setMode(EnumModeObjet.C_MO_CONSULTATION);//ejb
				initEtatBouton();
				break;
			case C_MO_EDITION:
				if (silencieu || (!silencieu && MessageDialog.openQuestion(vue.getShell(), MessagesEcran
						.getString("Message.Attention"), MessagesEcran
						.getString("TypeNoteArticle.Message.Annuler")))) {
					int rang = modelTypeNote.getListeObjet().indexOf(selectedTypeNote.getValue());
					remetTousLesChampsApresAnnulationSWT(dbc);
					modelTypeNote.getListeObjet().set(rang, swtOldTypeNote);
					writableList = new WritableList(realm, modelTypeNote.getListeObjet(), classModel);
					tableViewer.setInput(writableList);
					tableViewer.refresh();
					tableViewer.setSelection(new StructuredSelection(swtOldTypeNote), true);
					dao.annuler(taTNote);
					hideDecoratedFields();
				}
				getModeEcran().setMode(EnumModeObjet.C_MO_CONSULTATION);//ejb
				initEtatBouton();

				break;
			case C_MO_CONSULTATION:
				actFermer();
				break;
			default:
				break;
			}
		} finally {
			VerrouInterface.setVerrouille(false);
		}
	}

	@Override
	protected void actImprimer() throws Exception {
//passage ejb
//		TaInfoEntrepriseDAO taInfoEntrepriseDAO = new TaInfoEntrepriseDAO(getEm());
//		TaInfoEntreprise taInfoEntreprise = taInfoEntrepriseDAO.findInstance();
//
//		String nomChampIdTable =  dao.getChampIdTable();
//
//		FonctionGetInfosXmlAndProperties fonctionGetInfosXmlAndProperties = new FonctionGetInfosXmlAndProperties(mapperUIToModel);
//		fonctionGetInfosXmlAndProperties.cleanValueMapAttributeTable();
//
//		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaTNoteArticle.class.getSimpleName()+".head");
//		fonctionGetInfosXmlAndProperties.setValueMapAttributeTable(TaTNoteArticle.class.getSimpleName()+".detail");
//		
//		LinkedHashMap<String,AttributElementResport> mapAttributeTablHead = fonctionGetInfosXmlAndProperties.getMapAttributeTablHead(); 
//		LinkedHashMap<String,AttributElementResport> mapAttributeTablDetail = fonctionGetInfosXmlAndProperties.getMapAttributeTablDetail();
//		
//		Collection<TaTNoteArticle> collectionTaTNote = modelTypeNote.getListeEntity();
//		
//		fonctionGetInfosXmlAndProperties.findInfosFileXml(TaTNoteArticle.class.getName(),TaTNoteArticleDTO.class.getName(),
//				listeChamp,"mapping");
//		fonctionGetInfosXmlAndProperties.getInfosObjetJPA(taTNote);
//
//		ConstEdition constEdition = new ConstEdition(); 
////		Impression impression = new Impression(constEdition,TaTNote,collectionTaTNote,nomChampIdTable,TaTNote.getIdTWeb());
//		String nomDossier = null;
//
//		int nombreLine = collectionTaTNote.size();
//
//		if(nombreLine==0){
//			MessageDialog.openWarning(vue.getShell(), ConstEdition.TITRE_MESSAGE_EDITION_VIDE,
//					ConstEdition.EDITION_VIDE);
//		}
//		else{
//			if(taInfoEntreprise.getIdInfoEntreprise()==0){
//				nomDossier = ConstEdition.INFOS_VIDE;
//			}
//			else{
//				nomDossier = taInfoEntreprise.getNomInfoEntreprise();	
//			}
//
//			constEdition.addValueList(tableViewer, nomClassController);
//
//			/**
//			 * pathFileReport ==> le path de ficher de edition dynamique
//			 */
//			String folderEditionDynamique = Const.C_RCP_INSTANCE_LOCATION+"/"+Const.C_NOM_PROJET_TMP+"/"+TaTNoteArticle.class.getSimpleName();
//			constEdition.makeFolderEditions(folderEditionDynamique);
//			Path pathFileReport = new Path(folderEditionDynamique+"/"+Const.C_NOM_VU_T_NOTE_ARTICLE+".rptdesign");
//			final String pathFileReportDynamic = pathFileReport.toPortableString();
//
//			MakeDynamiqueReport DynamiqueReport = new MakeDynamiqueReport(constEdition.getNameTableEcran(),
//					constEdition.getNameTableBDD(),pathFileReportDynamic,Const.C_NOM_VU_T_NOTE_ARTICLE,
//					ConstEdition.PAGE_ORIENTATION_LANDSCAPE,nomDossier); 
//			DynamiqueReport.setSimpleNameEntity(TaTNoteArticle.class.getSimpleName());
//			/**************************************************************/
//			DynamiqueReport.setFonctionGetInfosXml(fonctionGetInfosXmlAndProperties);
//			DynamiqueReport.setNomObjet(TaTNoteArticle.class.getSimpleName());
//			/**************************************************************/
//
//			Map<String, AttributElementResport> attribuGridHeader = new LinkedHashMap<String, AttributElementResport>();
//			String nameHeaderTitle = ConstEditionArticle.SOUS_TITLE_EDITION_ARTICLE_FAMILLE;
//			attribuGridHeader.put(nameHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
//					ConstEdition.FONT_SIZE_XX_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
//					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,""));
//			String nameSousHeaderTitle = ConstEditionArticle.SOUS_TITLE_EDITION_ARTICLE_FAMILLE;
//			attribuGridHeader.put(nameSousHeaderTitle, new AttributElementResport("",ConstEdition.TEXT_ALIGN_CENTER,
//					ConstEdition.FONT_SIZE_X_LARGE,ConstEdition.FONT_WEIGHT_BOLD,"",
//					ConstEdition.COLUMN_DATA_TYPE_STRING,ConstEdition.UNITS_VIDE,ConstEdition.COLOR_GRAY));
//
//			//DynamiqueReport.buildDesignConfig();
//			ConstEdition.CONTENT_COMMENTS = ConstEditionArticle.COMMENTAIRE_EDITION_DEFAUT;
//			DynamiqueReport.initializeBuildDesignReportConfig();
//			DynamiqueReport.makePageMater("1", "1", "1", "1", "100");
//			DynamiqueReport.makeReportHeaderGrid(3,5,100,ConstEdition.UNITS_PERCENTAGE,attribuGridHeader);
////			DynamiqueReport.makeReportTableDB(100,ConstEdition.UNITS_PERCENTAGE,
////					Const.C_NOM_VU_T_TEL,attribuTabHeader,attribuTabDetail,1,1,2,5,"30");
//			DynamiqueReport.biuldTableReport("100", ConstEdition.UNITS_PERCENTAGE, 
//					Const.C_NOM_VU_T_NOTE_ARTICLE,1,1,2,"40", mapAttributeTablHead, mapAttributeTablDetail);
//			DynamiqueReport.savsAsDesignHandle();
////			impression.imprimer(true,pathFileReportDynamic,null,"Type Web",TaTNote.class.getSimpleName(),false);
//			
//			/** 01/03/2010 modifier les editions (zhaolin) **/
//			BaseImpressionEdition impressionEdition = new BaseImpressionEdition(constEdition,taTNote,
//					getEm(),collectionTaTNote,taTNote.getIdTNoteArticle());
//			impressionEdition.impressionEditionTypeEntity(pathFileReportDynamic,"Web");
//		}
	}
	
	@Override
	protected boolean aideDisponible() {
		boolean result = false;
		switch ((getThis().getModeEcran().getMode())) {
		case C_MO_CONSULTATION:
			if(getFocusCourantSWT().equals(vue.getGrille()))
				result = true;
			break;
		case C_MO_EDITION:
		case C_MO_INSERTION:
			break;
		default:
			break;
		}
		return result;
	}

	@Override
	protected void actAide() throws Exception {
		actAide(null);
	}

	@Override
	protected void actAide(String message) throws Exception {
		if(aideDisponible()){
			try {
				VerrouInterface.setVerrouille(true);
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_WAIT));
				ParamAfficheAideRechercheSWT paramAfficheAideRecherche = new ParamAfficheAideRechercheSWT();
				paramAfficheAideRecherche.setMessage(message);
				// Creation de l'ecran d'aide principal
				Shell s = new Shell(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),LgrShellUtil.styleLgr);
				PaAideSWT paAide = new PaAideSWT(s, SWT.NONE);
				SWTPaAideControllerSWT paAideController = new SWTPaAideControllerSWT(paAide);
				/***************************************************************/
				LgrPartListener.getLgrPartListener().setLgrActivePart(null);
				IEditorPart e = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(new EditorInputAide(), EditorAide.ID);
				LgrPartListener.getLgrPartListener().setLgrActivePart(e);
				LgrPartListener.getLgrPartListener().getLgrNavigation().add(e);
				paAideController = new SWTPaAideControllerSWT(((EditorAide)e).getComposite());
				((EJBLgrEditorPart)e).setController(paAideController);
				((EJBLgrEditorPart)e).setPanel(((EditorAide)e).getComposite());
				/***************************************************************/
				EJBBaseControllerSWTStandard controllerEcranCreation = null;
				ParamAffiche parametreEcranCreation = null;
				IEditorPart editorCreation = null;
				String editorCreationId = null;
				IEditorInput editorInputCreation = null;
				Shell s2 = new Shell(s, LgrShellUtil.styleLgr);

				switch ((getThis().getModeEcran().getMode())) {
				case C_MO_CONSULTATION:
					if(getFocusCourantSWT().equals(vue.getGrille())){
						PaTypeNoteArticleSWT paTypeNoteSWT = new PaTypeNoteArticleSWT(s2,SWT.NULL);
						SWTPaTypeNoteArticleController swtPaTypeNoteController = new SWTPaTypeNoteArticleController(paTypeNoteSWT);

						editorCreationId = EditorTypeNoteArticle.ID;
						editorInputCreation = new EditorInputTypeNoteArticle();

						ParamAfficheTypeNoteArticle ParamAfficheTypeNote = new ParamAfficheTypeNoteArticle();
						paramAfficheAideRecherche.setJPQLQuery(dao.getJPQLQuery());
						ParamAfficheTypeNote.setModeEcran(EnumModeObjet.C_MO_INSERTION);
						ParamAfficheTypeNote.setEcranAppelant(paAideController);
						controllerEcranCreation = swtPaTypeNoteController;
						parametreEcranCreation = ParamAfficheTypeNote;

						paramAfficheAideRecherche.setTypeEntite(TaTNoteArticle.class);
						paramAfficheAideRecherche.setChampsRecherche(Const.C_CODE_T_NOTE_ARTICLE);
						paramAfficheAideRecherche.setDebutRecherche(vue.getTfCODE_T_NOTE_ARTICLE().getText());
						paramAfficheAideRecherche.setControllerAppelant(getThis());
						paramAfficheAideRecherche.setModel(swtPaTypeNoteController.getModelTypeNote());
						paramAfficheAideRecherche.setTypeObjet(swtPaTypeNoteController.getClassModel());
						paramAfficheAideRecherche.setChampsIdentifiant(Const.C_ID_T_NOTE_ARTICLE);
					}
					break;
				case C_MO_EDITION:
				case C_MO_INSERTION:
					break;
				default:
					break;
				}

				if (paramAfficheAideRecherche.getJPQLQuery() != null) {

					PaAideRechercheSWT paAideRecherche1 = new PaAideRechercheSWT(s,SWT.NULL);
					SWTPaAideRechercheControllerSWT paAideRechercheController1 = new SWTPaAideRechercheControllerSWT(
							paAideRecherche1);

					// Parametrage de la recherche
					paramAfficheAideRecherche.setFocusSWT(((PaAideRechercheSWT) paAideRechercheController1.getVue()).getTfChoix());
					paramAfficheAideRecherche.setRefCreationSWT(controllerEcranCreation);
					paramAfficheAideRecherche.setEditorCreation(editorCreation);
					paramAfficheAideRecherche.setEditorCreationId(editorCreationId);
					paramAfficheAideRecherche.setEditorInputCreation(editorInputCreation);
					paramAfficheAideRecherche.setParamEcranCreation(parametreEcranCreation);
					paramAfficheAideRecherche.setShellCreation(s2);
					paAideRechercheController1.configPanel(paramAfficheAideRecherche);

					// Ajout d'une recherche
					paAideController.addRecherche(paAideRechercheController1,
							paramAfficheAideRecherche.getTitreRecherche());

					// Parametrage de l'ecran d'aide principal
					ParamAfficheAideSWT paramAfficheAideSWT = new ParamAfficheAideSWT();
					ParamAfficheAide paramAfficheAide = new ParamAfficheAide();

					// enregistrement pour le retour de l'ecran d'aide
					paAideController.addRetourEcranListener(getThis());
					Control focus = vue.getShell().getDisplay().getFocusControl();
					// affichage de l'ecran d'aide principal (+ ses recherches)

					dbc.getValidationStatusMap().removeMapChangeListener(changeListener);
					/*****************************************************************/
					paAideController.configPanel(paramAfficheAide);
					/*****************************************************************/
					dbc.getValidationStatusMap().addMapChangeListener(changeListener);

				}

			} finally {
				VerrouInterface.setVerrouille(false);
				vue.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_ARROW));
			}
		}
	}
	public IStatus validateUI() {
		return null;
	}
	
	public IStatus validateUIField(String nomChamp,Object value) {
		
		IStatus resultat = new Status(IStatus.OK,ArticlesPlugin.PLUGIN_ID,"validateUIField champ : "+nomChamp+" valeur : "+value!=null?value.toString():"valeur nulle"+" validation OK");
		
		int VALIDATION_CLIENT = 1;
		int VALIDATION_SERVEUR = 2;
		int VALIDATION_CLIENT_ET_SERVEUR = 3;
		
		//int TYPE_VALIDATION = VALIDATION_CLIENT;
		//int TYPE_VALIDATION = VALIDATION_SERVEUR;
		int TYPE_VALIDATION = VALIDATION_CLIENT_ET_SERVEUR;
		
		AbstractApplicationDAOClient<TaTNoteArticleDTO> validationClient = new AbstractApplicationDAOClient<TaTNoteArticleDTO>();
		
		//validation client
		if(TYPE_VALIDATION==VALIDATION_CLIENT || TYPE_VALIDATION==VALIDATION_CLIENT_ET_SERVEUR) {
			//BeanValidatorJFaceDatabinding validator = new BeanValidatorJFaceDatabinding(TaTNoteArticleDTO.class,nomChamp);
			//resultat = validator.validate(selectedTypeNote.getValue());
			try {
				TaTNoteArticleDTO f = new TaTNoteArticleDTO();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				//a.validate((TaTNoteArticleDTO)selectedTypeNote.getValue(), nomChamp, null);
				validationClient.validate(f, nomChamp, ITaTNoteArticleServiceRemote.validationContext);
			} catch(Exception e) {
				//if(resultat==null) {
					//resultat = new MultiStatus(TiersPlugin.PLUGIN_ID, 0, e.getMessage(), e);
					resultat = new Status(IStatus.ERROR,ArticlesPlugin.PLUGIN_ID, e.getMessage(), e);
				//}
			}
		}
		//validation serveur
		if(resultat==null && (TYPE_VALIDATION==VALIDATION_SERVEUR || TYPE_VALIDATION==VALIDATION_CLIENT_ET_SERVEUR)) {
			try {
				TaTNoteArticleDTO f = new TaTNoteArticleDTO();
				PropertyUtils.setSimpleProperty(f, nomChamp, value);
				//dao.validateDTOProperty((TaTNoteArticleDTO)selectedTypeNote.getValue(), nomChamp);
				dao.validateDTOProperty(f, nomChamp, ITaTNoteArticleServiceRemote.validationContext);
			} catch(Exception e) {
				//if(resultat==null) {
					//resultat = new MultiStatus(TiersPlugin.PLUGIN_ID, 0, e.getMessage(), e);
					resultat = new Status(IStatus.ERROR,ArticlesPlugin.PLUGIN_ID, e.getMessage(), e);
				//}
				//e.printStackTrace();
			}
		}
		
		return resultat;
		
//		try {
//			IStatus s = null;
//			boolean verrouilleModifCode = false;
//			int change=0;
//				TaTNoteArticle u = new TaTNoteArticle();
//				PropertyUtils.setSimpleProperty(u, nomChamp, value);
//				if(((TaTNoteArticleDTO) selectedTypeNote.getValue()).getIdTNoteArticle()!=null) {
//					u.setIdTNoteArticle(((TaTNoteArticleDTO) selectedTypeNote.getValue()).getIdTNoteArticle());
//				}
//				if(nomChamp.equals(Const.C_CODE_T_NOTE_ARTICLE)){
//					verrouilleModifCode = true;
//				}
//
//				s = dao.validate(u,nomChamp,validationContext,verrouilleModifCode);
//				if(s.getSeverity()!=IStatus.ERROR && change!=0) {
//					  
//				}
//			return s;
//		} catch (IllegalAccessException e) {
//			logger.error("",e);
//		} catch (InvocationTargetException e) {
//			logger.error("",e);
//		} catch (NoSuchMethodException e) {
//			logger.error("",e);
//		}
//		return null;
	}
	
	@Override
	protected void actEnregistrer() throws Exception {
//		EntityTransaction transaction = dao.getEntityManager().getTransaction();
		try {
			ctrlUnChampsSWT(getFocusCourantSWT());
			ctrlTousLesChampsAvantEnregistrementSWT();
//			dao.begin(transaction);
			if ((modeEcran.getMode().compareTo(EnumModeObjet.C_MO_EDITION) == 0)||
					(modeEcran.getMode().compareTo(EnumModeObjet.C_MO_INSERTION) == 0)) {

//mapper sur client, envoi d'une entité		
//				TaTNoteArticleMapper mapper = new TaTNoteArticleMapper();
//				mapper.mapDtoToEntity((TaTNoteArticleDTO) selectedTypeNote.getValue(),taTNote);
//				
//				if (modeEcran.getMode().compareTo(EnumModeObjet.C_MO_EDITION) == 0)
//					taTNote=dao.enregistrerMerge(taTNote);
//				else if (modeEcran.getMode().compareTo(EnumModeObjet.C_MO_INSERTION) == 0)
//					dao.enregistrerPersist(taTNote);
					
//mapper sur serveur, envoi d'un DTO					
					if (modeEcran.getMode().compareTo(EnumModeObjet.C_MO_EDITION) == 0)
						dao.enregistrerMergeDTO((TaTNoteArticleDTO) selectedTypeNote.getValue(),ITaTNoteArticleServiceRemote.validationContext);
					else if (modeEcran.getMode().compareTo(EnumModeObjet.C_MO_INSERTION) == 0)
						dao.enregistrerPersistDTO((TaTNoteArticleDTO) selectedTypeNote.getValue(),ITaTNoteArticleServiceRemote.validationContext);

			} 
//			dao.commit(transaction);
			modelTypeNote.addEntity(taTNote);
			
			getModeEcran().setMode(EnumModeObjet.C_MO_CONSULTATION);//ejb
			
//			transaction = null;
			actRefresh();
			
			//nettoyage affichage erreur possible
			hideDecoratedFields();
			vue.getLaMessage().setText("");
		} catch(Exception e) {
			e.printStackTrace();
			if(e.getMessage()!=null)
				vue.getLaMessage().setText(e.getMessage());
			
			afficheDecoratedField(vue.getTfCODE_T_NOTE_ARTICLE(),e.getMessage());
			
		} finally {
//			if(transaction!=null && transaction.isActive()) {
//				transaction.rollback();
//			}
			initEtatBouton();
		}
	}


	public void initEtatComposant() {
		try {
			vue.getTfCODE_T_NOTE_ARTICLE().setEditable(!isUtilise());
			//vue.getTfLABELLE_T_WEB().setEditable(!isUtilise());
			changeCouleur(vue);
	    } catch (Exception e) {
		vue.getLaMessage().setText(e.getMessage());
	    }
	}

	public boolean isUtilise(){
		return (((TaTNoteArticleDTO)selectedTypeNote.getValue()).getId()!=null &&
		!dao.recordModifiable(dao.getNomTable(),
				((TaTNoteArticleDTO)selectedTypeNote.getValue()).getId()))||
				!dao.autoriseModification(taTNote);		
	}
	
	public TaTNoteArticleDTO getSwtOldTypeNote() {
		return swtOldTypeNote;
	}

	public void setSwtOldTypeNote(TaTNoteArticleDTO swtOldTypeNote) {
		this.swtOldTypeNote = swtOldTypeNote;
	}
	

	public void setSwtOldTypeNote() {
		if (selectedTypeNote.getValue() != null)
			this.swtOldTypeNote = TaTNoteArticleDTO.copy((TaTNoteArticleDTO) selectedTypeNote.getValue());
		else {
			if(tableViewer.selectionGrille(0)){
				this.swtOldTypeNote = TaTNoteArticleDTO.copy((TaTNoteArticleDTO) selectedTypeNote.getValue());
				tableViewer.setSelection(new StructuredSelection(
						(TaTNoteArticleDTO) selectedTypeNote.getValue()), true);
			}
		}
	}
	public void setVue(PaTypeNoteArticleSWT vue) {
		super.setVue(vue);
		this.vue = vue;
	}

	@Override
	protected void initMapComposantDecoratedField() {
		if (mapComposantDecoratedField == null)
			mapComposantDecoratedField = new LinkedHashMap<Control, ControlDecoration>();
		mapComposantDecoratedField.put(vue.getTfCODE_T_NOTE_ARTICLE(), vue.getFieldCODE_T_NOTE_ARTICLE());
		mapComposantDecoratedField.put(vue.getTfLIBELLE_T_NOTE_ARTICLE(), vue.getFieldLABELLE_T_NOTE_ARTICLE());
	}

	public Class getClassModel() {
		return classModel;
	}

	@Override
	protected void sortieChamps() {
		// TODO Raccord de methode auto-genere
		
	}

	@Override
	protected void actRefresh() throws Exception {
		TaTNoteArticle u = taTNote;
		if (u!=null && u.getIdTNoteArticle()==0)
			u=dao.findById(u.getIdTNoteArticle());
		writableList = new WritableList(realm, modelTypeNote.remplirListe(), classModel);
		tableViewer.setInput(writableList);
		tableViewer.refresh();
		tableViewer.selectionGrille(
				tableViewer.selectionGrille(selectedTypeNote.getValue()));
		
		if(u != null) {
			Iterator<TaTNoteArticleDTO> ite = modelTypeNote.getListeObjet().iterator();
			TaTNoteArticleDTO tmp = null;
			int i = 0;
			boolean trouve = false;
			while (ite.hasNext() && !trouve) {
				tmp = ite.next();
				if(tmp.getId()==u.getIdTNoteArticle()) {
					tableViewer.setSelection(new StructuredSelection(tmp));
					trouve = true;
				}
				i++;
			}
		}	
	}

	public ModelGeneralObjetEJB<TaTNoteArticle,TaTNoteArticleDTO> getModelTypeNote() {
		return modelTypeNote;
	}

	public ITaTNoteArticleServiceRemote getDao() {
		return dao;
	}

	public void setDao(ITaTNoteArticleServiceRemote dao) {
		this.dao = dao;
	}

	public void actAfficherTous() throws Exception{
		vue.getGrille().setVisible(true);  
		vue.getBtnTous().setVisible(false);
		vue.getLaTitreGrille().setVisible(true);
		vue.getCompositeForm().setWeights(new int[]{50,100});
		dao.setJPQLQuery(dao.getJPQLQueryInitial());
		modelTypeNote.setJPQLQuery(null);
		modelTypeNote.setListeEntity(null);
		try {
			actRefresh();
		} catch (Exception e1) {
			logger.error("", e1);
		}
	}
}