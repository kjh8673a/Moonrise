
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    
    // 주문
    @Transactional
    public Long order(Long memberId, long itemId, int count){
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 이 로직은 다른데서 생성할 때 매번 달라질 수 있기 때문에 일관성있는 메소드를 제공하는 것이 좋음.
//        OrderItem orderItem1 = new OrderItem();
//        orderItem1.setCount();
        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        //order save만 해도 cascade 옵션으로 인하여 저장이 됨.
        orderRepository.save(order);

        return order.getId();
    }

    // 취소
    @Transactional
    public void cancelOrder(Long orderId){
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        //주문 취소
        order.cancel();
    }
    // 검색
//    public List<Order> findOrders(OrderSearch orderSearch){
//        return orderRepository.findAll(orderSearch);
//    }
}
