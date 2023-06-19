public class BruteCollinearPoints {
    LineSegment[] lineSegments;
    int numberOfNode = 0;
    private static final double elipson = 1e-12;
    
    public BruteCollinearPoints(Point[] points){
        int l = points.length;
        LineSegmentNode head = null;
        // p→q→r→s
        for (int i0 = 0; i0 < l; i0++) {
            Point p = points[i0];
            for (int i1 = i0 + 1; i1 < l; i1++) {
                Point q = points[i1];
                for (int i2 = i1 + 1; i2 < l; i2++) {
                    Point r = points[i2];
                    for (int i3 = i2 + 1; i3 < l; i3++) {
                        Point s = points[i3];
                        double pqSlope = p.slopeTo(q);
                        double prSlope = p.slopeTo(r);
                        if (Math.abs(pqSlope - prSlope) < elipson) {
                            double psSlope = p.slopeTo(s);
                            if (Math.abs(pqSlope - psSlope) < elipson) {
                                head = new LineSegmentNode(new LineSegment(p, r), head);
                                numberOfNode++;
                            }
                        }
                    }
                }
            }
        }
        lineSegments = new LineSegment[numberOfNode];
        for (int i = 0; i < numberOfNode; i++) {
            lineSegments[i] = head.item;
            head = head.next;
        } 

    }    // finds all line segments containing 4 points

    public int numberOfSegments() {
        return numberOfNode;
    }        // the number of line segments

    public LineSegment[] segments() {
        return lineSegments;

    }               // the line segments

    private class LineSegmentNode {
        LineSegmentNode next;
        LineSegment item;

        public LineSegmentNode(LineSegment item, LineSegmentNode next) {
            this.item = item;
            this.next = next;
        }
    }
}