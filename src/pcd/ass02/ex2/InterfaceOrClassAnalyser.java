package pcd.ass02.ex2;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * Visitor used for project analysis
 * 
 * @author aricci
 *
 */
class InterfaceOrClassAnalyser extends VoidVisitorAdapter<ElemNotifier> {
		
	public void visit(PackageDeclaration fd, ElemNotifier notifier) {
		super.visit(fd, notifier);
		notifier.notifyNewPackageDeclared(fd.getNameAsString());
	}

	public void visit(ClassOrInterfaceDeclaration cd, ElemNotifier notifier) {
		super.visit(cd, notifier);
		if (cd.isInterface()) {
			notifier.notifyNewInterface(cd.getNameAsString());
		} else {
			notifier.notifyNewClass(cd.getNameAsString());
		}
	}
	
	public void visit(FieldDeclaration fd,  ElemNotifier notifier) {
		super.visit(fd, notifier);
		var name = fd.getVariable(0).getName().getIdentifier();
		notifier.notifyNewField(name);
	}

	public void visit(MethodDeclaration md,  ElemNotifier notifier) {
		super.visit(md, notifier);
		var name = md.getName().asString();
		notifier.notifyNewMethod(name);
	}
}