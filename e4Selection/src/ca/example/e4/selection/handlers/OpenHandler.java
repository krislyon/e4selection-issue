package ca.example.e4.selection.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.widgets.Shell;

import ca.example.e4.selection.model.Album;
public class OpenHandler {

	@Execute
	public void execute(Shell shell){
//		FileDialog dialog = new FileDialog(shell);
//		dialog.open();
	}
	
	@CanExecute
	public boolean canExecute( @Optional @Named(IServiceConstants.ACTIVE_SELECTION) Album selectedAlbum ) {
		System.out.println("CanExecute invoked with: " + ((selectedAlbum == null)? "null" : selectedAlbum.toString() ) );		
		if( selectedAlbum == null ) return false;
		return selectedAlbum.isOpenable();
	}
}
