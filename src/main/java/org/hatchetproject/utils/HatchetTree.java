package org.hatchetproject.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class HatchetTree<T> implements Iterable<T> {
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private Iterator<Node<T>> nodeIterator = root.iterator();

            @Override
            public boolean hasNext() {
                return nodeIterator.hasNext();
            }

            @Override
            public T next() {
                return nodeIterator.next().get();
            }
        };
    }

    static class Node<T> implements Iterable<Node<T>> {
        private List<Node<T>> children;
        private Node<T> parent;
        private T element;

        private Integer rootDistance;


        Node(T element, Node<T> parent) {
            this.element = element;
            setParent(parent);
            this.parent.addChild(this);
            this.children = new ArrayList<>();
        }

        Node (T element) {
            this(element, null);
        }

        public Node<T> getParentNode() {
            return parent;
        }

        public boolean isRoot() {
            return parent == null;
        }

        public void addChild(Node<T> child) {
            this.children.add(child);
        }

        public T get() {
            return element;
        }

        public T getParent() {
            return parent.get();
        }

        public void setParent(Node<T> parent) {
            this.parent = parent;
        }

        public void removeChildNode(Node<T> child) {
            children.remove(child);
        }

        public List<Node<T>> getChildrenNodes() {
            return children;
        }

        public int childrenSize() {
            return children.size();
        }

        public List<T> getChildren() {
            List<T> childList = new ArrayList<>(children.size());
            for (Node<T> child : children) {
                childList.add(child.get());
            }
            return childList;
        }

        public Node<T> getChildNode(int idx) {
            return children.get(idx);
        }

        public T getChild(int idx) {
            return children.get(idx).get();
        }

        public T removeChild(int idx) {
            Node<T> child = children.remove(idx);
            child.setParent(null);
            return child.get();
        }

        public int getRootDistance() {
            if (rootDistance == null) {
                if (isRoot())
                    return 0;
                else
                    return parent.getRootDistance() + 1;
            }
            return rootDistance;
        }

        public boolean hasPrecalculatedRootDistance() {
            return rootDistance != null;
        }

        public void calculateRootDistance() {
            if (isRoot()) {
                rootDistance = 0;
                return;
            }
            Node<T> element = this;
            int distance = 0;
            while (element.parent != null || element.hasPrecalculatedRootDistance()) {
                element = element.parent;
                distance += element.hasPrecalculatedRootDistance() ? element.getRootDistance() + 1 : 1;
            }
        }

        public boolean hasChildren() {
            return !(children == null || children.isEmpty());
        }

        @Override
        public Iterator<Node<T>> iterator() {
            return new Iterator<Node<T>>() {

                private LinkedList<Node<T>> stack = new LinkedList<Node<T>>(){
                    {
                        add(Node.this);
                    }
                };

                @Override
                public boolean hasNext() {
                    return stack.isEmpty();
                }

                @Override
                public Node<T> next() {
                    if (stack.isEmpty())
                        throw new NoSuchElementException("Iterator reached the end");
                    Node<T> lastElm = stack.pollLast();
                    if (lastElm.hasChildren())
                        stack.addAll(lastElm.getChildrenNodes());
                    return lastElm;
                }
            };
        }
    }

    private Map<T, Node<T>> mappingToNodes = new HashMap<>();

    private Node<T> root;

    private int maxHeight = 0;

    public void add(T parent, T child) {
        addNode(parent, new Node<T>(child));
    }

    public void add(T parent, HatchetTree<T> tree) {
        validateElementPresence(parent);
        if (hasElementsInTree(tree))
            throw new IllegalArgumentException("Some elements are already in tree");
        Node<T> parentalNode = mappingToNodes.get(parent);
        parentalNode.addChild(tree.root);
        tree.root.setParent(parentalNode);
        mappingToNodes.putAll(tree.mappingToNodes);
    }

    public void addNode(T parent, Node<T> treeNodes) {
        validateElementPresence(parent);
        if (hasElementsInTree(treeNodes))
            throw new IllegalArgumentException("Some elements are already in tree");
        if (!treeNodes.isRoot())
            throw new IllegalArgumentException("Can not assign non root node");
        Node<T> parentalNode = mappingToNodes.get(parent);
        parentalNode.addChild(treeNodes);
        treeNodes.setParent(parentalNode);
        mappingToNodes.put(treeNodes.get(), treeNodes);
        mapSubNodes(treeNodes);
    }

    private void mapSubNodes(Node<T> node) {
        for (Node<T> subNode : node) {
            mappingToNodes.put(subNode.get(), subNode);
        }
    }

    private void validateElementPresence(T element) {
        if (!mappingToNodes.containsKey(element))
            throw new NoSuchElementException("Element is not present in tree");
    }

    private boolean hasElementsInTree(Node<T> node) {
        if (mappingToNodes.containsKey(node.get()))
            return true;
        if (!node.hasChildren())
            return false;
        for (Node<T> child : node) {
            if (mappingToNodes.containsKey(child.get()))
                return true;
        }
        return false;
    }

    public boolean hasElementsInTree(HatchetTree<T> tree) {
        Set<T> commonElements = new HashSet<T>(this.mappingToNodes.keySet());
        commonElements.retainAll(tree.mappingToNodes.keySet());
        return !commonElements.isEmpty();
    }

    public T getParent(T node) {
        if (!mappingToNodes.containsKey(node)) {
            throw new NoSuchElementException("Element is not present in tree");
        }
        Node<T> childNode = mappingToNodes.get(node);
        if (childNode.isRoot())
            return null;
        return childNode.getParent();
    }

    public Node<T> remove(T node) {
        if (!mappingToNodes.containsKey(node))
            return null;
        Node<T> childNode = mappingToNodes.remove(node);
        removeMaps(childNode);
        childNode.getParentNode().removeChildNode(childNode);
        childNode.setParent(null);
        return childNode;
    }

    private void removeMaps(Node<T> node) {
        for (Node<T> subNode : node) {
            mappingToNodes.remove(subNode.get());
        }
    }

    public int getDistanceFromRoot(T element) {
        if (!mappingToNodes.containsKey(element))
            return -1;
        return mappingToNodes.get(element).getRootDistance();
    }

    public boolean contains(T node) {
        return mappingToNodes.containsKey(node);
    }

    public Set<T> getElements() {
        return mappingToNodes.keySet();
    }

    public int size() {
        return mappingToNodes.size();
    }
}
