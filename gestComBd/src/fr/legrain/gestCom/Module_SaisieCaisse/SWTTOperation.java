package fr.legrain.gestCom.Module_SaisieCaisse;

import fr.legrain.lib.data.ModelObject;

// Generated Mar 1, 2007 11:26:10 AM by Hibernate Tools 3.2.0.b9

/**
 * SWTUnite generated by hbm2java
 */
public class SWTTOperation extends ModelObject {
	private Integer idTOperation;
	private String codeTOperation;
	private String liblTOperation;


	public SWTTOperation(Integer idTOperation, String codeTOperation,
			String liblTOperation) {
		this.idTOperation = idTOperation;
		this.codeTOperation = codeTOperation;
		this.liblTOperation = liblTOperation;
	}

	public static SWTTOperation copy(SWTTOperation swtTOperation){
		SWTTOperation swtTOperationLoc = new SWTTOperation();
		swtTOperationLoc.codeTOperation = swtTOperation.codeTOperation;
		swtTOperationLoc.idTOperation = swtTOperation.idTOperation;
		swtTOperationLoc.liblTOperation = swtTOperation.liblTOperation;		
		return swtTOperationLoc;		
	}
	
	public SWTTOperation() {
	}


	public Integer getIdTOperation() {
		return idTOperation;
	}


	public void setIdTOperation(Integer idTOperation) {
		firePropertyChange("idTOperation", this.idTOperation, this.idTOperation = idTOperation);
	}


	public String getCodeTOperation() {
		return codeTOperation;
	}


	public void setCodeTOperation(String codeTOperation) {
		firePropertyChange("codeTOperation", this.codeTOperation, this.codeTOperation = codeTOperation);
	}


	public String getLiblTOperation() {
		return liblTOperation;
	}


	public void setLiblTOperation(String liblTOperation) {
		firePropertyChange("liblTOperation", this.liblTOperation, this.liblTOperation = liblTOperation);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codeTOperation == null) ? 0 : codeTOperation.hashCode());
		result = prime * result
				+ ((idTOperation == null) ? 0 : idTOperation.hashCode());		
		result = prime * result
				+ ((liblTOperation == null) ? 0 : liblTOperation.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SWTTOperation other = (SWTTOperation) obj;
		if (codeTOperation == null) {
			if (other.codeTOperation != null)
				return false;
		} else if (!codeTOperation.equals(other.codeTOperation))
			return false;
		if (idTOperation != other.idTOperation)
			return false;
		if (liblTOperation == null) {
			if (other.liblTOperation != null)
				return false;
		} else if (!liblTOperation.equals(other.liblTOperation))
			return false;
		return true;
	}

}