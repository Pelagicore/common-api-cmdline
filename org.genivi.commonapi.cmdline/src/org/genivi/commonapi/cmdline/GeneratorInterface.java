package org.genivi.commonapi.cmdline;

import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.franca.core.franca.FModel;
import org.franca.deploymodel.dsl.fDeploy.FDInterface;

/**
 * Interface to be implemented by generators
 * @author jguillou
 *
 */
public interface GeneratorInterface {

	public void generate(FModel fModel, List<FDInterface> deployedInterfaces,
			IFileSystemAccess fileSystemAccess, IResource res);

}
