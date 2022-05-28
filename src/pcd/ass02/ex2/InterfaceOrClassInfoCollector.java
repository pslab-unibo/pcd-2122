package pcd.ass02.ex2;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * Visitor used to build the reports
 * 
 * @author aricci
 *
 */
class InterfaceOrClassInfoCollector extends VoidVisitorAdapter<BaseReportImp> {

	public void visit(PackageDeclaration fd, BaseReportImp rep) {
		super.visit(fd, rep);
		rep.setPackageName(fd.getNameAsString());
	}

	public void visit(ClassOrInterfaceDeclaration cd, BaseReportImp rep) {
		super.visit(cd, rep);
		rep.setFullName(cd.getNameAsString());
	}
	
	public void visit(FieldDeclaration fd,  BaseReportImp rep) {
		super.visit(fd, rep);
		var name = fd.getVariable(0).getName().getIdentifier();
		var stype = fd.getVariable(0).getTypeAsString();
		rep.addField(new FieldInfoImp(name, stype, rep));
	}

	public void visit(MethodDeclaration md,  BaseReportImp rep) {
		super.visit(md, rep);
		var name = md.getName().asString();
		var beginLine = md.getRange().get().begin.line;
		var endLine = md.getRange().get().end.line;
		rep.addMethod(new MethodInfoImp(name, beginLine, endLine, rep));
	}
}