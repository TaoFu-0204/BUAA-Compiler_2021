package Optimizer;

import IR.IRElem;
import IR.IRImmSymbol;
import IR.IRLabelSymbol;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class JumpOpt {
    private LinkedList<IRElem> iRList;

    public JumpOpt(LinkedList<IRElem> irList) {
        iRList = irList;
    }

    public void condBranchOpt() {
        HashSet<IRElem> delSet = new HashSet<>();
        ArrayList<Integer> delIdSet = new ArrayList<>();
        for (int i = 0; i < iRList.size(); i++) {
            IRElem inst = iRList.get(i);
            if (inst.getType() == IRElem.BZ) {
                if (inst.getOp1() instanceof IRImmSymbol) {
                    if (((IRImmSymbol) inst.getOp1()).getValue() == 0) {
                        IRElem newInst = new IRElem(IRElem.BR, inst.getOp3());
                        iRList.set(i, newInst);
                    } else {
                        delSet.add(inst);
                        delIdSet.add(i);
                    }
                }
            } else if (inst.getType() == IRElem.BNZ) {
                if (inst.getOp1() instanceof IRImmSymbol) {
                    if (((IRImmSymbol) inst.getOp1()).getValue() != 0) {
                        IRElem newInst = new IRElem(IRElem.BR, inst.getOp3());
                        iRList.set(i, newInst);
                    } else {
                        delSet.add(inst);
                        delIdSet.add(i);
                    }
                }
            }
        }
        /*for (IRElem inst : delSet) {
            iRList.remove(inst);
        }*/
        for (int i = delIdSet.size() - 1; i >= 0; i--) {
            iRList.remove((int) delIdSet.get(i));
        }
    }

    public LinkedList<IRElem> doJumpOpt() {
        condBranchOpt();
        IRElem first = null;
        IRElem next = null;
        int firstPos = 0, nextPos = 1;
        while (firstPos < iRList.size() - 1) {
            nextPos = firstPos + 1;
            first = iRList.get(firstPos);
            if (first.getType() == IRElem.BR || first.getType() == IRElem.BZ || first.getType() == IRElem.BNZ) {
                boolean removed = false;
                IRLabelSymbol jumpLabel = (IRLabelSymbol) first.getOp3();
                next = iRList.get(nextPos);
                while (next.getType() == IRElem.LABEL) {
                    IRLabelSymbol nextLabel = (IRLabelSymbol) next.getOp3();
                    if (jumpLabel.getId() == nextLabel.getId()) { // ?????????????????????????????????
                        iRList.remove(firstPos); // ????????????????????????
                        removed = true;
                        break;
                    } else {
                        ++nextPos;
                        if (nextPos >= iRList.size()) {
                            break;
                        }
                        next = iRList.get(nextPos);
                    }
                }
                if (removed) {
                    firstPos -= 1; // ??????????????????????????????
                } else {
                    firstPos = nextPos; // ?????????nextPos????????????????????????Label???????????????????????????
                }
            } else {
                firstPos += 1;
            }
        }
        return iRList;
    }
}
